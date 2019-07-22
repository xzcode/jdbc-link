package com.xzcode.jdbclink.core.resolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.models.SqlAndParams;
import com.xzcode.jdbclink.core.pool.string.StringBuilderPool;
import com.xzcode.jdbclink.core.sql.Delete;
import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.groupby.GroupByParam;
import com.xzcode.jdbclink.core.sql.groupby.having.Having;
import com.xzcode.jdbclink.core.sql.join.Join;
import com.xzcode.jdbclink.core.sql.limit.LimitParam;
import com.xzcode.jdbclink.core.sql.order.OrderParam;
import com.xzcode.jdbclink.core.sql.param.Param;
import com.xzcode.jdbclink.core.sql.param.ParamGroup;
import com.xzcode.jdbclink.core.sql.param.UpdateParam;
import com.xzcode.jdbclink.core.sql.param.select.SelectParam;
import com.xzcode.jdbclink.core.sql.update.Update;
import com.xzcode.jdbclink.core.sql.update.UpdateSet;
import com.xzcode.jdbclink.core.sql.where.Where;


/**
 * sql解析器
 * 
 * 
 * @author zai
 * 2018-09-08 17:53:53
 */
public class SqlResolver implements ISqlResolver {
	
	protected JdbcLinkConfig config;
	
	
	public SqlResolver(JdbcLinkConfig config) {
		this.config = config;
	}

	@Override
	public SqlAndParams handelSelect(Select<?> select) {
		
		StringBuilderPool stringBuilderPool = config.getStringBuilderPool();
		StringBuilder sql = stringBuilderPool.get();
		StringBuilder columnsSql = stringBuilderPool.get();
		
		

		EntityInfo entityInfo = select.getEntityInfo();
		
		
		
		List<SelectParam> selectParams = select.getSelectParams();
		
		List<Object> args = new ArrayList<>();
		
		if (selectParams == null) {
			select.column(select.getMainAlias(), "*");
			selectParams = select.getSelectParams();
		}
		
			
		for (SelectParam sp : selectParams) {
			
			if (sp.getType() != SelectParam.TypeConstant.SQL_PART) {
					if ("*".equals(sp.getColumn())) {
						List<SelectParam> transferAllColumns = transferAllColumns(select, sp.getTableAlias());
						for (SelectParam selectParam : transferAllColumns) {
							columnsSql
							.append(selectParam.getSqlPart())
							.append(",");
							;
						}
					}else {
						columnsSql
						.append(sp.getTableAlias())
						.append(".`")
						.append(sp.getColumn())
						.append("`");
						if (StringUtils.isNotEmpty(sp.getAlias())) {
							columnsSql.append(" ").append(sp.getAlias()).append(" ");
						}
						columnsSql.append(",");
						;
					}
					
			}else {
				columnsSql
				.append(sp.getSqlPart())
				.append(",");
				;
				
			}
			
			
			
		
		}
		
		/*for (SelectParam x : selectParams) {
			columnsSql
			.append(x.getTableAlias())
			.append(x.getTableAlias() == "" ? "" : '.')
			.append(x.getColumn())
			.append(" ")
			.append(x.getAlias())
			.append(" ")
			.append(",");
			;
		}*/
		columnsSql.setLength(columnsSql.length() - 1);
			
		
		
		
		
		
		sql.append(" from ");
		sql.append("`").append(select.getEntityInfo().getDatabase()).append("`.");
		sql.append(entityInfo.getTable())
		.append(" ");
		if (StringUtils.isNoneEmpty(select.getMainAlias())) {
			sql.append(select.getMainAlias());
			sql.append(" ");
		}
		
		
		
		if (select.getJoins() != null) {
			
			for (String alias : select.getJoins().keySet()) {
				
				Join<?, ?> sJoin = select.getJoins().get(alias);
				
				sql
				.append(sJoin.getJoinTag())
				.append(" ");
				sql.append("`").append(sJoin.getEntityInfo().getDatabase()).append("`").append(".");
				sql.append(sJoin.getEntityInfo().getTable())
				.append(" ")
				.append(alias)
				.append(" ")
				;
				
				if ( sJoin.getParams() != null || sJoin.getParamGroups() != null) {
					sql.append(" on ");
						
					int handleParams = handleParams(sJoin.getParams(), sql, args, stringBuilderPool);
					
					handleParamGroups(sJoin.getParamGroups(),handleParams, sql, args, stringBuilderPool);
				}
				
			}
			
		}
		
		
		
		Where<?, ?> where = select.getWhere();
		
		if (where != null) {
			
			
			StringBuilder whereSql = stringBuilderPool.get();
			
			int handleParams = handleParams(where.getParams(), whereSql, args, stringBuilderPool);
			
			handleParamGroups(where.getParamGroups(),handleParams, whereSql, args, stringBuilderPool);
			
			if (whereSql.length() > 0) {
				sql.append(" where ").append(whereSql);
				
				stringBuilderPool.returnOject(whereSql);
			}
		}
		
		List<GroupByParam> groupByParams = select.getGroupByParams();
		
		if (groupByParams != null) {
			sql.append(" group by ");
			
			for (GroupByParam groupByParam : groupByParams) {
				if (StringUtils.isNotEmpty(groupByParam.getAlias())) {
					sql.append(groupByParam.getAlias()).append(".");
				}
				sql.append("`").append(groupByParam.getColumn().fieldName()).append("`").append(",");
			}
			sql.setLength(sql.length()-1);
			
			sql.append(" ");
			
			
			Having<?, ?> having = select.getHaving();
			
			if (having != null) {
			
				StringBuilder havingColumnsSql = stringBuilderPool.get();
				
				int handledParamsNums = handleParams(having.getParams(), havingColumnsSql, args, stringBuilderPool);
				
				int handleParamGroups = handleParamGroups(having.getParamGroups(), handledParamsNums, havingColumnsSql, args, stringBuilderPool);
				
				if (handledParamsNums > 0 || handleParamGroups > 0) {
					havingColumnsSql.insert(0, " having ");
				}
			
				sql.append(havingColumnsSql);
				
				stringBuilderPool.returnOject(havingColumnsSql);
			}
		}
		
		SqlAndParams sqlAndParams = new SqlAndParams();
		//创建count
		if (select.isCreateCountSql()) {
			makeCountSql(sqlAndParams, args, sql);
		}
		
		//插入查询列
		sql.insert(0, columnsSql);
		
		//插入select 关键词
		sql.insert(0, " select ");
		
		//回收对象
		stringBuilderPool.returnOject(columnsSql);
		
		
		
		
		
		
		List<?> orderParams = select.getOrderParams();
		
		if (orderParams != null && orderParams.size() > 0) {
			
			sql.append(" order by ");
			
			for (Object orderObj : orderParams) {
				OrderParam<?> order = (OrderParam<?>) orderObj;
				sql
				.append(order.getTableAlias())
				.append(order.getTableAlias() == "" ? "" : ".")
				.append(order.getOrderBy())
				.append(" ")
				.append(order.getSortBy())
				.append(",");
			}
			
			sql.setLength(sql.length() - 1);
		}
		
		LimitParam limitParam = select.getLimit();
		
		if (limitParam != null && limitParam.getPageSize() != null && limitParam.getStarts() != null) {
				
			sql.append(" limit ?, ? ");
			args.add(limitParam.getStarts());
			args.add(limitParam.getPageSize());
				
		}
		
		sqlAndParams.setArgs(args);
		
		sqlAndParams.setSql(sql.toString());
		
		stringBuilderPool.returnOject(sql);
		
		return sqlAndParams;
		
	}
	
	/**
	 * 处理update语句
	 * @param update
	 * @param stringBuilderPool
	 * @return
	 * 
	 * @author zai
	 * 2017-06-13
	 */
	@Override
	public SqlAndParams handelUpdate(Update update) {
		
			StringBuilderPool stringBuilderPool = config.getStringBuilderPool();
			
			EntityInfo entityInfo = update.getEntityInfo();
			StringBuilder sql = stringBuilderPool.get();
			
			List<Object> args = new LinkedList<>();
			
			List<UpdateParam> updateParams = update.getSet().getParams();
			
			sql.append(" update ");
			sql.append("`").append(entityInfo.getDatabase()).append("`").append(".");
			sql.append("`").append(entityInfo.getTable()).append("`")
			.append(" set ")
			;
			
			for (UpdateParam updateParam : updateParams) {
				if (updateParam.getIsSatisfy()) {
					
					//如果是字段类型
					if(updateParam.getType() == UpdateParam.TypeConstant.ENTITY_FIELD) {
					
						if (updateParam.getField2() != null) {
							sql
							.append("`").append(updateParam.getField().fieldName()).append("`")
							.append(" = `")
							.append(updateParam.getField2().fieldName())
							.append("`,");
						}else {
							sql
							.append("`").append(updateParam.getField().fieldName()).append("`")
							.append(" = ? ,");
							args.add(updateParam.getVal());
						}
						
					
					}else if (updateParam.getType() == UpdateParam.TypeConstant.SQL_PART) {
						sql.append(updateParam.getSqlpart()).append(",");
					}
					
				}
			}
			sql.setLength(sql.length() - 1);
			
			
			Where<UpdateSet, UpdateSet> where = update.getSet().getWhere();
			
			if (where != null) {
				
				StringBuilder whereSql = stringBuilderPool.get();
				
				
				
				int handleParams = handleParams(where.getParams(), whereSql, args, stringBuilderPool);
				
				handleParamGroups(where.getParamGroups(),handleParams, whereSql, args, stringBuilderPool);
				
				if (whereSql.length() > 0) {
					sql.append(" where ").append(whereSql);
					
					stringBuilderPool.returnOject(whereSql);
				}
				
			}
			
			LimitParam limitParam = update.getSet().getLimit();
			
			if (limitParam != null && limitParam.getPageSize() != null) {
					
				sql.append(" limit ? ");
				args.add(limitParam.getPageSize());
					
			}
			
			//sql.append(" ; ");
			
			SqlAndParams sqlAndParams = new SqlAndParams();
			sqlAndParams.setArgs(args);
			sqlAndParams.setSql(sql.toString());
			stringBuilderPool.returnOject(sql);
			
			return sqlAndParams;
		}
	
	
	/**
	 * 处理update语句
	 * @param delete
	 * @param stringBuilderPool
	 * @return
	 * 
	 * @author zai
	 * 2017-06-13
	 */
	@Override
	public SqlAndParams handelDelete(Delete delete) {
		
			StringBuilderPool stringBuilderPool = config.getStringBuilderPool();
			
			EntityInfo entityInfo = delete.getEntityInfo();
			StringBuilder sql = stringBuilderPool.get();
			
			List<Object> args = new LinkedList<>();
			
			sql.append(" delete from ");
			sql.append("`").append(entityInfo.getDatabase()).append("`").append(".");
			sql.append("`").append(entityInfo.getTable()).append("`")
			;
			
			if (delete.getWhere() != null) {
				
				List<Param<Where<Delete, Delete>>> params = delete.getWhere().getParams();
				List<ParamGroup<Where<Delete, Delete>>> paramGroups = delete.getWhere().getParamGroups();
				
				StringBuilder whereSql = stringBuilderPool.get();
				
				
				
				int handledParamsNums = handleParams(params, whereSql, args, stringBuilderPool);
				
				handleParamGroups(paramGroups, handledParamsNums,whereSql, args, stringBuilderPool);
				
				if (whereSql.length() > 0) {
					sql.append(" where ").append(whereSql);
					
					stringBuilderPool.returnOject(whereSql);
				}
			}
			
			LimitParam limitParam = delete.getLimit();
			
			if (limitParam != null && limitParam.getPageSize() != null) {
					
				sql.append(" limit ? ");
				args.add(limitParam.getPageSize());
					
			}
			
			//sql.append(" ; ");
			
			SqlAndParams sqlAndParams = new SqlAndParams();
			sqlAndParams.setArgs(args);
			sqlAndParams.setSql(sql.toString());
			stringBuilderPool.returnOject(sql);
			
			return sqlAndParams;
		}

	private void makeCountSql(
			SqlAndParams sqlAndParams, 
			List<Object> args, 
			StringBuilder sqlPart
			){
		List<Object> countParams = new ArrayList<>();
		countParams.addAll(args);
		sqlAndParams.setCountParams(countParams);
		StringBuilderPool stringBuilderPool = config.getStringBuilderPool();
		StringBuilder countSql = config.getStringBuilderPool().get();
		countSql.append("select count(*) ").append(sqlPart);
		String countSqlStr = countSql.toString();
		if (countSqlStr.contains("group by")) {
			countSqlStr = "select count(*) from ("+countSqlStr+") t";
		}
		sqlAndParams.setCountSql(countSqlStr);
		stringBuilderPool.returnOject(countSql);
	}
	
	/**
	 * 处理分组查询参数
	 * @param paramGroups
	 * @param sql
	 * @param args
	 * @param stringBuilderPool
	 * 
	 * @author zai 2017-06-08 22:23:36
	 * @param params 
	 */
	private int handleParamGroups(List<?> paramGroups, int handledParamsNums, StringBuilder sql, List<Object> args, StringBuilderPool stringBuilderPool){
		
		if (paramGroups == null) {
			return 0;
		}
		
		int nums = 0;
		
		for (int index = 0; index < paramGroups.size(); index++) {
			
			ParamGroup<?> paramGroup = (ParamGroup<?>) paramGroups.get(index);
			
			if(paramGroup.getIsSatisfy() && handleParamGroup(paramGroup, handledParamsNums + nums, sql, args, stringBuilderPool)){
				nums++;
			}
			
		}
		
		return nums;
		
	}
	
	private  boolean handleParamGroup(ParamGroup<?> paramGroup, int handledParamsNums, StringBuilder sql, List<Object> args, StringBuilderPool stringBuilderPool){
		
		boolean success = true;
		StringBuilder groupSql = stringBuilderPool.get();
		
		int paramNums = handleParams(paramGroup.getParams(), groupSql, args, stringBuilderPool);
		
		List<?> paramGroups = paramGroup.getParamGroups();
		
		if (paramNums > 0 || (paramGroups != null && paramGroups.size() > 0)) {
			if (handledParamsNums > 0) {
				sql.append(paramGroup.getConnect()).append(" ");
			}
			
			groupSql.insert(0, " ( ");
			
			handleParamGroups(paramGroup.getParamGroups(), paramNums, groupSql, args, stringBuilderPool);
			
			groupSql.append(" ) ");
			
			
		}else {
			success = false;
		}
		
		sql.append(groupSql);
		
		stringBuilderPool.returnOject(groupSql);
		
		return success;
	}


	/**
	 * 处理查询参数
	 * @param params
	 * @param sql
	 * @param args
	 * @param stringBuilderPool
	 * 
	 * @author zai 2017-06-08 22:23:01
	 */
	private  int handleParams(List<?> params, StringBuilder sql, List<Object> args, StringBuilderPool stringBuilderPool){
		
		if (params == null) {
			return 0;
		}
		int i = 0;
		for(int index = 0; index < params.size(); index++){
			if(handleParam((Param<?>) params.get(index), sql, args, i, stringBuilderPool)){
				i++;
			}
			
		};
		return i;
		
	}
	
	/**
	 * 处理条件参数与sql拼接
	 * @param param
	 * @param sql
	 * @param args
	 * @param index
	 * 
	 * @author zai
	 * 2017-06-08
	 * @param stringBuilderPool 
	 */
	private  boolean handleParam(
			Param<?> param, 
			StringBuilder sql, 
			List<Object> args,
			int handledNums, 
			StringBuilderPool stringBuilderPool){
		
			if (!param.getIsSatisfy()) {
				return false;
			}
			
			if (param.getType() == Param.TypeConstant.SQL_PART) {
				sql.append(param.getConnect()).append(" ").append(param.getSqlpart()).append(" ");
				return true;
			}
			
			
			/*String keyAlias = null;
			if (param.getKey().contains(".")) {
				keyAlias = param.getKey().substring(0, param.getKey().indexOf("."));
			}*/
										
				switch (param.getTag()) {
				case "between":
					
					if (handledNums != 0) {
						sql.append(param.getConnect()).append(" ");
					}
					sql
					.append(" ")
					.append(param.getTableAlias())
					.append(".")
					.append("`").append(param.getField().fieldName()).append("`")
					.append(" ")
					.append(param.getTag())
					.append(" ? and ? ")
					;
					args.add(param.getVal());
					args.add(param.getVal2());
					
					break;
				
				case "in":
				case "not in":
					
					if (param.getValues() != null && param.getValues().length > 0) {
						
						if (handledNums != 0) {
							sql.append(param.getConnect()).append(" ");
						}
						
						sql
						.append(" ")
						.append(param.getTableAlias())
						.append(".")
						.append("`").append(param.getField().fieldName()).append("`")
						.append(" ")
						.append(param.getTag())
						.append(" (");
						
						for (int i = 0; i < param.getValues().length; i++) {
							sql.append("?,");
							args.add(param.getValues()[i]);
						}
						
						sql.setLength(sql.length() - 1);
						
						sql.append(") ");
					
					}
					
					;
					break;
					
				case "locate":
					
					if (handledNums != 0) {
						sql.append(param.getConnect()).append(" ");
					}
					
					sql
					.append(" locate( ?, ")
					.append(param.getTableAlias())
					.append(".")
					.append("`").append(param.getField().fieldName()).append("`")
					.append(") > 0 ")
					;
					args.add(param.getVal());
					
					break;
					
				default:
					
					if (handledNums != 0) {
						sql.append(param.getConnect()).append(" ");
					}
					
					sql
					.append(" ")
					.append(param.getTableAlias())
					.append(".")
					.append("`").append(param.getField().fieldName()).append("`")
					.append(" ")
					.append(param.getTag())
					.append(" ");
					if (param.getField2() != null) {
						sql
						.append(param.getTableAlias2())
						.append(".")
						.append("`").append(param.getField2().fieldName()).append("`")
						.append(" ");
					}else{
						sql.append(" ? ");
						args.add(param.getVal());
					};
					break;
				}
				
				return true;
			
		}
	
	/**
	 * 把 * 查询转换为所有列查询
	 * @param tableAlias
	 * 
	 * @author zai
	 * 2018-05-10
	 */
	private  List<SelectParam> transferAllColumns(Select<?> select, String tableAlias) {
		SelectParam selectParam = null;
		
		EntityInfo entityInfo = null;
		
		if (tableAlias == null || tableAlias.equals("") || tableAlias.equals(select.getMainAlias())) {
			entityInfo = select.getEntityInfo();
		}else if (select.getJoins() != null && select.getJoins().containsKey(tableAlias)) {
			entityInfo = select.getJoins().get(tableAlias).getEntityInfo();
		}else {
			throw new RuntimeException("There is no such table alias:" + tableAlias + "!");
		}
		
		//entityInfo = select.getEntityInfo();
		List<EntityFieldInfo> fieldInfos = entityInfo.getFieldInfos();
		int fieldInfosSize = fieldInfos.size();
		List<SelectParam> list = new ArrayList<>(fieldInfosSize);
		
		for (int i = 0; i < fieldInfosSize; i++) {
			
			selectParam = new SelectParam();
			selectParam.setType(SelectParam.TypeConstant.SQL_PART);
			
			selectParam.setSqlPart(tableAlias + ".`" + fieldInfos.get(i).getColumn() + "` " + fieldInfos.get(i).getPropName());
			
			/*
			selectParam.setTableAlias(tableAlias);
			selectParam.setField(fieldInfos.get(i).getColumn());
			selectParam.setAlias(fieldInfos.get(i).getPropName());
			*/
			
			selectParam.setSelect(select);
			list.add(selectParam);
			
		}
		return list;
		
	}
		


}
