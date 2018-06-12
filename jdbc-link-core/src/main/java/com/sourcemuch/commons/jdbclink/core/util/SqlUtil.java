package com.sourcemuch.commons.jdbclink.core.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;
import com.sourcemuch.commons.jdbclink.core.models.SqlAndParams;
import com.sourcemuch.commons.jdbclink.core.pool.string.StringBuilderPool;
import com.sourcemuch.commons.jdbclink.core.sql.Delete;
import com.sourcemuch.commons.jdbclink.core.sql.Select;
import com.sourcemuch.commons.jdbclink.core.sql.groupby.GroupByParam;
import com.sourcemuch.commons.jdbclink.core.sql.groupby.having.Having;
import com.sourcemuch.commons.jdbclink.core.sql.join.Join;
import com.sourcemuch.commons.jdbclink.core.sql.limit.LimitParam;
import com.sourcemuch.commons.jdbclink.core.sql.order.OrderParam;
import com.sourcemuch.commons.jdbclink.core.sql.param.Param;
import com.sourcemuch.commons.jdbclink.core.sql.param.ParamGroup;
import com.sourcemuch.commons.jdbclink.core.sql.param.UpdateParam;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.SelectParam;
import com.sourcemuch.commons.jdbclink.core.sql.update.Update;
import com.sourcemuch.commons.jdbclink.core.sql.update.UpdateSet;
import com.sourcemuch.commons.jdbclink.core.sql.where.Where;

public class SqlUtil {
	
	public static SqlAndParams handelSelect(
		Select<?> select,
		boolean createCountSql
		) {
		
		StringBuilderPool stringBuilderPool = select.getStringBuilderPool();
		StringBuilder sql = stringBuilderPool.get();
		StringBuilder columnsSql = stringBuilderPool.get();
		
		

		EntityInfo entityInfo = select.getEntityInfo();
		
		List<Object> args = new LinkedList<>();
		
		//List<String> selectColumns = null;
		
		List<SelectParam> selectParams = select.getSelectParams();
		
		//Map<String, AliasAndPrefix> joinsMap = select.getJoins();
		
		if (selectParams == null) {
			select.column("*");
			selectParams = select.getSelectParams();
		}
		
			
		for (SelectParam x : selectParams) {
			
			if (x.getColumn().equals("*")) {
				List<SelectParam> transferAllColumns = transferAllColumns(select, x.getTableAlias());
				for (SelectParam x2 : transferAllColumns) {
					columnsSql
					.append(x2.getTableAlias())
					.append(x2.getTableAlias() == "" ? "" : '.')
					.append(x2.getColumn())
					.append(" ")
					.append(x2.getAlias())
					.append(" ")
					.append(",");
					;
				}
			}else {
				columnsSql
				.append(x.getTableAlias())
				.append(x.getTableAlias() == "" ? "" : '.')
				.append(x.getColumn())
				.append(" ")
				.append(x.getAlias())
				.append(" ")
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
			
		
		
		
		
		
		sql
		.append(" from ")
		.append(entityInfo.getTable())
		.append(" ");
		if (StringUtils.isNoneEmpty(select.getMainAlias())) {
			sql.append(select.getMainAlias());
			sql.append(" ");
		}
		;
		
		
		
		if (select.getJoins() != null) {
			
			for (String alias : select.getJoins().keySet()) {
				
				Join<?, ?> sJoin = select.getJoins().get(alias);
				
				sql
				.append(sJoin.getJoinTag())
				.append(" ")
				.append(sJoin.getEntityInfo().getTable())
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
		
		SqlAndParams sqlAndParams = new SqlAndParams();
		//创建count
		if (createCountSql) {
			makeCountSql(sqlAndParams, args, sql, stringBuilderPool);
		}
		
		//插入查询列
		sql.insert(0, columnsSql);
		
		//插入select 关键词
		sql.insert(0, " select ");
		
		//回收对象
		stringBuilderPool.returnOject(columnsSql);
		
		
		List<GroupByParam> groupByParams = select.getGroupByParams();
		
		if (groupByParams != null) {
			sql.append(" group by ");
			
			for (GroupByParam groupByParam : groupByParams) {
				if (StringUtils.isNotEmpty(groupByParam.getAlias())) {
					sql.append(groupByParam.getAlias()).append(".");
				}
				sql.append(groupByParam.getColumn()).append(",");
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
		
		
		List<OrderParam> orderParams = select.getOrderParams();
		
		if (orderParams != null && orderParams.size() > 0) {
			
			sql.append(" order by ");
			
			for (OrderParam order : orderParams) {
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
	public static SqlAndParams handelUpdate(Update update,StringBuilderPool stringBuilderPool) {
			
			EntityInfo entityInfo = update.getEntityInfo();
			StringBuilder sql = stringBuilderPool.get();
			
			List<Object> args = new LinkedList<>();
			
			List<UpdateParam> updateParams = update.getSet().getParams();
			
			sql
			.append(" update ")
			.append(entityInfo.getTable())
			.append(" set ")
			;
			
			for (UpdateParam updateParam : updateParams) {
				if (updateParam.getIsSatisfy()) {
					
					if (updateParam.getKey2() != null) {
						sql
						.append(updateParam.getKey())
						.append(" = ")
						.append(updateParam.getKey2())
						.append(",");
					}else {
						sql
						.append(updateParam.getKey())
						.append(" = ? ,");
						args.add(updateParam.getVal());
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
	public static SqlAndParams handelDelete(Delete delete,StringBuilderPool stringBuilderPool) {
			
			EntityInfo entityInfo = delete.getEntityInfo();
			StringBuilder sql = stringBuilderPool.get();
			
			List<Object> args = new LinkedList<>();
			
			sql
			.append(" delete from ")
			.append(entityInfo.getTable())
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

	private static void makeCountSql(
			SqlAndParams sqlAndParams, 
			List<Object> args, 
			StringBuilder sqlPart,
			StringBuilderPool stringBuilderPool
			){
		List<Object> countParams = new ArrayList<>();
		countParams.addAll(args);
		sqlAndParams.setCountParams(countParams);
		StringBuilder countSql = stringBuilderPool.get();
		countSql.append("select count(*) ").append(sqlPart);;
		sqlAndParams.setCountSql(countSql.toString());
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
	private static int handleParamGroups(List<?> paramGroups, int handledParamsNums, StringBuilder sql, List<Object> args, StringBuilderPool stringBuilderPool){
		
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
	
	private static boolean handleParamGroup(ParamGroup<?> paramGroup, int handledParamsNums, StringBuilder sql, List<Object> args, StringBuilderPool stringBuilderPool){
		
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
	private static int handleParams(List<?> params, StringBuilder sql, List<Object> args, StringBuilderPool stringBuilderPool){
		
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
	private static boolean handleParam(
			Param<?> param, 
			StringBuilder sql, 
			List<Object> args,
			int handledNums, 
			StringBuilderPool stringBuilderPool){
		
			if (!param.getIsSatisfy()) {
				return false;
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
					.append(param.getTableAlias()).append(param.getTableAlias() == "" ? "" : ".")
					.append(param.getKey())
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
						.append(param.getTableAlias()).append(param.getTableAlias() == "" ? "" : ".")
						.append(param.getKey())
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
					.append(param.getTableAlias()).append(param.getTableAlias() == "" ? "" : ".")
					.append(param.getKey())
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
					.append(param.getTableAlias() == "" ? "" : ".")
					.append(param.getKey())
					.append(" ")
					.append(param.getTag())
					.append(" ");
					if (param.getKey2() != null) {
						sql
						.append(param.getTableAlias2())
						.append(param.getTableAlias2() == "" ? "" : ".")
						.append(param.getKey2())
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
	private static List<SelectParam> transferAllColumns(Select<?> select, String tableAlias) {
		SelectParam selectParam = null;
		
		EntityInfo entityInfo = null;
		if (tableAlias == null || tableAlias.equals("") || tableAlias.equals(select.getMainAlias())) {
			entityInfo = select.getEntityInfo();
		}else if (select.getJoins() != null && select.getJoins().containsKey(tableAlias)) {
			entityInfo = select.getJoins().get(tableAlias).getEntityInfo();
		}else {
			throw new RuntimeException("There is no such table alias:" + tableAlias + "!");
		}
		List<SelectParam> list = new ArrayList<>(entityInfo.getColumns().size());
		for (int i = 0; i < entityInfo.getColumns().size(); i++) {
			String col = entityInfo.getColumns().get(i);
			selectParam = new SelectParam();
			selectParam.setTableAlias(tableAlias);
			selectParam.setColumn(col);
			selectParam.setAlias(entityInfo.getProps().get(i));
			selectParam.setSelect(select);
			list.add(selectParam);
		}
		return list;
		
	}
		
	
	/**
	 * 出来参数子查询
	 * @param select
	 * @param sql
	 * @param args
	 * @param stringBuilderPool
	 * 
	 * @author zai 2017-06-08 22:22:41
	 */
	/*private static void handelParamSubSelect(Select<?> select, StringBuilder sql, List<Object> args, StringBuilderPool stringBuilderPool){
		SqlAndParams sqlAndParams = SqlUtil.handelSelect(select, false);
		sql.append(sqlAndParams.getSql());
		args.addAll(sqlAndParams.getArgs());
	}*/


}
