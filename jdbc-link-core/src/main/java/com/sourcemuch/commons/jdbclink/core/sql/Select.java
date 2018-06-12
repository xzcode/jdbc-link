package com.sourcemuch.commons.jdbclink.core.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;
import com.sourcemuch.commons.jdbclink.core.JdbcLink;
import com.sourcemuch.commons.jdbclink.core.cache.IEntityInfoCache;
import com.sourcemuch.commons.jdbclink.core.format.DefaultKeyFormatter;
import com.sourcemuch.commons.jdbclink.core.format.DefaultValueFormatter;
import com.sourcemuch.commons.jdbclink.core.format.KeyFormatter;
import com.sourcemuch.commons.jdbclink.core.format.ValueFormatter;
import com.sourcemuch.commons.jdbclink.core.models.Pager;
import com.sourcemuch.commons.jdbclink.core.models.SqlAndParams;
import com.sourcemuch.commons.jdbclink.core.pool.string.StringBuilderPool;
import com.sourcemuch.commons.jdbclink.core.sql.groupby.GroupByParam;
import com.sourcemuch.commons.jdbclink.core.sql.groupby.having.Having;
import com.sourcemuch.commons.jdbclink.core.sql.impl.EntityResultSetExtractor;
import com.sourcemuch.commons.jdbclink.core.sql.impl.EntityRowMapper;
import com.sourcemuch.commons.jdbclink.core.sql.impl.ListMapRowMapper;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.AliasAndPrefix;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.GroupByAble;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.HavingAble;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.JoinAble;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.LimitAble;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.OrderAble;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.QueryAble;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.WhereAble;
import com.sourcemuch.commons.jdbclink.core.sql.join.Join;
import com.sourcemuch.commons.jdbclink.core.sql.limit.LimitParam;
import com.sourcemuch.commons.jdbclink.core.sql.order.OrderParam;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.EachRowMapExecution;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.EachRowMapParam;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.SelectParam;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.SingleClolumnListMapExecution;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.SingleColumnParam;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.concat.ConcatColumn;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.concat.ConcatValue;
import com.sourcemuch.commons.jdbclink.core.sql.param.select.concat.IConcatParam;
import com.sourcemuch.commons.jdbclink.core.sql.where.Where;
import com.sourcemuch.commons.jdbclink.core.util.ShowSqlUtil;
import com.sourcemuch.commons.jdbclink.core.util.SqlUtil;

public class Select<T> extends AbstractCommon implements HavingAble<Select<T>, T>,GroupByAble<Select<T>>,WhereAble<Select<T>, T>,JoinAble<Select<T>, T>,LimitAble<Select<T>>, OrderAble<T>, QueryAble<T>, AliasAndPrefix{
	
	protected String mainAlias = ""; //主表别名
	
	protected boolean distinct = false;
	
	//private List<String> columns;
	
	private List<EachRowMapParam> eachRowMapParams;
	
	private List<SingleColumnParam> singleColumnParams;
	
	private List<SelectParam> selectParams;
	
	private List<GroupByParam> groupByParams;
	
	protected EntityInfo entityInfo;
	
	protected List<OrderParam> orderParams;
	
	protected LimitParam limit;

	protected String prefix = "";
	
	protected Class<T> clazz;
	
	protected Where<Select<T>, T> where;
	
	protected Having<Select<T>, T> having;
	
	protected Map<String, Join<Select<T>, T>> joins;
	
	protected KeyFormatter keyFormatter;
	
	protected ValueFormatter valueFormatter;
	
	protected JdbcLink jdbcLink;
	
	public Select(Class<T> clazz, JdbcTemplate jdbcTemplate, StringBuilderPool stringBuilderPool, IEntityInfoCache entityInfoCache) {
		this.jdbcTemplate = jdbcTemplate;
		this.stringBuilderPool = stringBuilderPool;
		this.entityInfoCache = entityInfoCache;
		this.clazz = clazz;
		this.entityInfo = super.entityInfoCache.getEntityInfo(clazz);
	}
	
	
	public Select<T> column(String tableAlias, String column, String alias) {
		
		SelectParam selectParam = null;
		selectParam = new SelectParam();
		selectParam.setTableAlias(tableAlias);
		selectParam.setColumn(column);
		selectParam.setAlias(alias);
		selectParam.setSelect(this);
		addSelectParams(selectParam);
		
		return this;
	}
	public Select<T> column(String tableAlias, String column) {
		return this.column(tableAlias, column, null);
	}
	
	public Select<T> column(String column) {
		return this.column(null, column, null);
	}
	
	
	public Select<T> columnIfNull(String column, Object defValue, String columnAlias) {
		
		return this.columnIfNull(null, column, defValue, columnAlias);
	}
	
	public Select<T> columnIfNull(String tableAlias, String column, Object defValue, String columnAlias) {
		if (defValue == null) {
			throw new NullPointerException("defValue can't not be null!");
		}
		Object defValuePart = null;
		
		if (defValue instanceof String) {
			defValuePart = "'" + defValue + "'";
		}else {
			defValuePart = defValue;
		}
		
		if (tableAlias == null) {
			tableAlias = "";
		}else {
			tableAlias += ".";
		}
		
		return this.column("IFNULL(" + tableAlias + column +", " + defValuePart +") as "+ columnAlias);
	}
	
	public Select<T> columnAlias(String column, String alias) {
		return this.column(null, column, alias);
	}
	
	public Select<T> columnForEach(EachRowMapExecution execution) {
		
		this.addEachRowMapParam(new EachRowMapParam(execution));
		
		return this;
	}
	
	public Select<T> columnForSingleCol(String resultAlias, String outerColumnName, String innerColumnName, SingleClolumnListMapExecution execution) {
		
		this.addSingleColumnParam(new SingleColumnParam(resultAlias, outerColumnName, innerColumnName, execution));
		return this;
	}
	
	public Select<T> columnForSingleCol(
			String resultAlias, 
			String outerColumnName,
			String innerColumnName, 
			Class<?> innerClazz
			) {
		
		this.addSingleColumnParam(new SingleColumnParam(resultAlias, outerColumnName, innerColumnName, new SingleClolumnListMapExecution() {

			@Override
			public List<Map<String, Object>> exec(Object[] colArr) {
				return jdbcLink.select(innerClazz).where().and().in(innerColumnName, colArr).queryListMap();
			}
			
		}));
		
		return this;
	}
	
	public Select<T> count(String tableAlias, String column, String alias) {
		return this.column("count("+tableAlias+"."+ column +") " + alias);
	}
	
	public Select<T> count(String column, String alias) {
		return this.column("count("+ column +") " + alias);
	}
	
	public Select<T> count(String column) {
		return this.column("count("+ column +")");
	}
	
	public Select<T> countAll(String alias) {
		return this.column("count(*) " + alias);
	}
	
	public Select<T> countAll() {
		return this.column("count(*)");
	}
	
	public Select<T> concat(String alias, IConcatParam...columns){
		return concat(columns, alias);
	}
	
	public Select<T> concat(IConcatParam[] columns, String alias) {
		StringBuilder sb = this.stringBuilderPool.get();
		sb.append("concat(");
		for (IConcatParam column : columns) {
			if (column instanceof ConcatColumn) {
				ConcatColumn cc = (ConcatColumn) column;
				if (StringUtils.isNotEmpty(cc.getTableAlias())) {
					sb.append(cc.getTableAlias());
					sb.append(".");				
				}
				sb.append(cc.getColumn());
				sb.append(",");
			}else if (column instanceof ConcatValue) {
				ConcatValue cv = (ConcatValue) column;
				sb.append("'").append(cv.getValue()).append("'");
				sb.append(",");
			}
			
		}
		sb.setLength(sb.length() - 1);
		sb.append(") ");
		sb.append(alias);
		
		Select<T> column = this.column(sb.toString());
		this.stringBuilderPool.returnOject(sb);
		
		return column;
	}
	
	/*public Select<T> concat(String[] columns, String alias) {
		StringBuilder sb = this.stringBuilderPool.get();
		sb.append("concat(");
		for (String column : columns) {
			if (StringUtils.isNotEmpty(column)) {
				sb.append(column);
				sb.append(".");				
			}
			sb.append(column);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append(") ");
		sb.append(alias);
		
		Select<T> column = this.column(sb.toString());
		this.stringBuilderPool.returnOject(sb);
		
		return column;
	}*/
	
	
	@SuppressWarnings("unchecked")
	public T selectById(Object uid) {
		Object entity = this.jdbcLink.select(entityInfo.getClazz())
		.where()
			.and().eq(this.entityInfo.getId(), uid)
		.queryEntity()
		;
		
		return (T) entity;
	}
	
	@SuppressWarnings("unchecked")
	public T selectByKey(String key, Object val) {
		return (T) this.jdbcLink.select(entityInfo.getClazz())
				.where()
					.and().eq(key, val)
				.queryEntity()
				;
	}

	
	public Select<T> distinct() {
		this.distinct = true;
		return this;
	}
	
	@Override
	public Select<T> orderBySorting(String orderBy, String sorting) {
		if (!sorting.equalsIgnoreCase("desc") && !sorting.equalsIgnoreCase("asc")) {
			throw new UnsupportedOperationException("Sorting param must be 'asc' or 'desc' !");
		}
		String lowerCase = orderBy.toLowerCase();
		if (lowerCase.length() > 100) {
			throw new IllegalArgumentException("OrderBy keyword is illeagal : " + orderBy);
		}
		
		this.addOrder(new OrderParam(orderBy, sorting));
		return this;
	}
	
	
	@Override
	public Select<T> orderBy(String orderBy) {
		this.addOrder(new OrderParam(orderBy, "asc"));
		return this;
	}
	
	@Override
	public Select<T> orderBy(String tableAlias, String orderBy) {
		this.addOrder(new OrderParam(tableAlias, orderBy, "asc"));
		return this;
	}
	
	@Override
	public Select<T> orderByAsc(String tableAlias,String orderBy) {
		this.addOrder(new OrderParam(tableAlias, orderBy, "asc"));
		return this;
	}
	
	@Override
	public Select<T> orderByAsc(boolean condition, String tableAlias, String orderBy) {
		OrderParam orderParam = new OrderParam(tableAlias, orderBy, "asc");
		orderParam.setSactisfy(condition);
		return this.addOrder(orderParam);
	}
	
	@Override
	public Select<T> orderByAsc(String orderBy) {
		this.addOrder(new OrderParam(orderBy, "asc"));
		return this;
	}
	
	@Override
	public Select<T> orderByAsc(boolean condition, String orderBy) {
		OrderParam orderParam = new OrderParam(orderBy, "asc");
		orderParam.setSactisfy(condition);
		return this.addOrder(orderParam);
	}
	
	@Override
	public Select<T> orderByDesc(String tableAlias,String orderBy) {
		this.addOrder(new OrderParam(tableAlias,orderBy, "desc"));
		return this;
	}
	
	@Override
	public Select<T> orderByDesc(boolean condition, String tableAlias, String orderBy) {
		OrderParam orderParam = new OrderParam(tableAlias,orderBy, "desc");
		orderParam.setSactisfy(condition);
		return this.addOrder(orderParam);
	}
	
	
	@Override
	public Select<T> orderByDesc(boolean condition, String orderBy) {
		OrderParam orderParam = new OrderParam(orderBy, "desc");
		orderParam.setSactisfy(condition);
		return this.addOrder(orderParam);
	}
	
	@Override
	public Select<T> orderByDesc(String orderBy) {
		this.addOrder(new OrderParam(orderBy, "desc"));
		return this;
	}
	
	public Select<T> addOrder(OrderParam orderParam) {
		if (orderParam.isSactisfy()) {
			if (this.orderParams == null ) {
				this.orderParams = new LinkedList<>();
			}
			this.orderParams.add(orderParam);
		}
		return this;
	}
	
	
	public <E> E queryObject(Class<E> ObjectClazz) {
		SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, false);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		try {
			
			return this.jdbcTemplate.queryForObject(sqlAndParams.getSql(), ObjectClazz, sqlAndParams.getArgs().toArray());
		} catch (EmptyResultDataAccessException  e) {
			return null;
		}
	}
	
	@Override
	public T queryEntity() {
		try {
			SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, false);
			ShowSqlUtil.debugSqlAndParams(sqlAndParams);
			return this.jdbcTemplate.query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(), new EntityResultSetExtractor<>(clazz, entityInfo));
		} catch (EmptyResultDataAccessException  e) {
			return null;
		}
	}
	
	@Override
	public Pager<T> pageEntity() {
		SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, true);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<T> items = this.jdbcTemplate.query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(), new EntityRowMapper<T>(clazz, entityInfo));
		
		Integer total = jdbcTemplate.queryForObject(sqlAndParams.getCountSql(), Integer.class, sqlAndParams.getCountParams());
		Pager<T> pager = new Pager<>();
		
		pager.setTotal(total);
		pager.setItems(items);
		pager.setPageNo(limit.getPageNo());
		pager.setPageSize(limit.getPageSize());
		
		return pager;
	}
	
	public List<T> queryList() {
		SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, false);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<T> list = this.jdbcTemplate.query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(), new EntityRowMapper<T>(clazz, entityInfo));
		return list;
	}
	
	public Map<String, Object> queryMap() {
		
		SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, false);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		Map<String, Object> resultMap = jdbcTemplate.query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(), new ResultSetExtractor<Map<String, Object>>(){

			@Override
			public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Object> map = new LinkedHashMap<>();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				while (rs.next()) {
					if (map == null) {
						map = new LinkedHashMap<>();
					}
					for(int i = 1; i<= columnCount; i++) {
					    map.put(getKeyFormatter().format(metaData.getColumnLabel(i)), getValueFormatter().format(rs.getObject(i)));
				   }
				}
				return map;
			}
		});
		
		if (resultMap != null &&(this.eachRowMapParams != null || this.singleColumnParams != null)) {
			List<Map<String, Object>> resultListMap = new LinkedList<>();
			resultListMap.add(resultMap);
			handleColumnExecution(resultListMap);
		}
		
		return resultMap;
	}
	
	@Override
	public List<Object> queryFirstColumn() {
		return (List<Object>) this.queryFirstColumn(Object.class);
	}
	
	@Override
	public <F> List<F> queryFirstColumn( Class<F> f) {
		SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, false);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<F> resultList = jdbcTemplate.query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(), new ResultSetExtractor<List<F>>(){

			@SuppressWarnings("unchecked")
			@Override
			public List<F> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<F> list = new LinkedList<>();
				
				while (rs.next()) {
					if (f == String.class) {
						list.add((F) String.valueOf(getValueFormatter().format(rs.getObject(1))));
					}else {
						list.add((F) getValueFormatter().format(rs.getObject(1)));
					}
				}
				return list;
			}
		});
		return resultList;
	}
	
	
	@Override
	public List<Map<String, Object>> queryListMap() {
		SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, false);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<Map<String, Object>> resultListMap = jdbcTemplate.query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(), new ListMapRowMapper(getKeyFormatter(), getValueFormatter()) );
		
		handleColumnExecution(resultListMap);
		
		return resultListMap;
	}
	
	private void handleColumnExecution(List<Map<String, Object>> resultListMap) {
		if (resultListMap != null && resultListMap.size() > 0) {
			
			
			if (this.eachRowMapParams != null || this.singleColumnParams != null) {
				
				try {
			
					if (this.eachRowMapParams != null) {
						for (Map<String, Object> map : resultListMap) {
							for (EachRowMapParam param : this.eachRowMapParams) {
								param.getExecution().exec(map);
							}
						}
					}
					
					if (this.singleColumnParams != null) {
						for (SingleColumnParam param : this.singleColumnParams) {
							
							Map<Object, Map<String, Object>> outIdMap = new LinkedHashMap<>();
							Set<Object> outIds = new LinkedHashSet<>(resultListMap.size());
							
							Object outId =  null;
							
							for (Map<String, Object> outMap : resultListMap) {
								outId = outMap.get(keyFormatter.format(param.getOuterColumnName()));
								if (outId != null) {
									outIdMap.put(outId, outMap);
									outIds.add(outId);
								}
								outMap.put(param.getResultKeyAlias(), null);
							}
							
							Object[] array = outIds.toArray();
							
							List<Map<String, Object>> subList = param.getExecution().exec(array);
							
							
							Object outPropId = null;
							
							if (subList != null && subList.size() > 0) {
								
								String innerColumnName = keyFormatter.format(param.getInnerColumnName());
								
								for (Map<String, Object> subMap : subList) {
									
									outPropId = subMap.get(innerColumnName);
									if (outPropId != null) {
										
										Map<String, Object> outMap = outIdMap.get(outPropId);
										@SuppressWarnings("unchecked")
										List<Map<String, Object>> subList2 = (List<Map<String, Object>>) outMap.get(param.getResultKeyAlias());
										if (subList2 == null) {
											subList2 = new LinkedList<>();
											outMap.put(param.getResultKeyAlias(), subList2);
										}
										subList2.add(subMap);
									}
									
								}
							}
						}
					}
				
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			
			}
	
		}
	}
	

	@Override
	public Pager<Map<String, Object>> pageListMap() {

		SqlAndParams sqlAndParams = SqlUtil.handelSelect(this, true);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<Map<String, Object>> items = jdbcTemplate.query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(), new ListMapRowMapper(getKeyFormatter(), getValueFormatter()) );
		
		handleColumnExecution(items);
		
		Integer total = jdbcTemplate.queryForObject(sqlAndParams.getCountSql(), sqlAndParams.getCountParams().toArray(), new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		
		pager.setTotal(total);
		pager.setItems(items);
		pager.setPageNo(limit.getPageNo());
		pager.setPageSize(limit.getPageSize());
		
		return pager;
	}
	
	
	public void addSelectParams(SelectParam selectParam){
		if (this.selectParams == null) {
			this.selectParams = new LinkedList<>();
		}
		this.selectParams.add(selectParam);
	}
	
	public Where<Select<T>, T> where() {
		this.where = new Where<>(this);
		return where;
	}
	
	public Where<Select<T>, T> getWhere() {
		return where;
	}
	
	@Override
	public void addJoins(String alias, Join<Select<T>, T> join) {
		if (this.joins == null) {
			this.joins = new LinkedHashMap<>();
		}
		this.joins.put(alias, join);
	}
	
	public Select<T> addGroupByParams(GroupByParam groupByParam) {
		if (this.groupByParams == null) {
			this.groupByParams = new LinkedList<>();
		}
		this.groupByParams.add(groupByParam);
		return this;
	}
	
	
	public Select<T> addEachRowMapParam(EachRowMapParam eachRowMapParam) {
		if (this.eachRowMapParams == null) {
			this.eachRowMapParams = new LinkedList<>();
		}
		this.eachRowMapParams.add(eachRowMapParam);
		return this;
	}
	
	public Select<T> addSingleColumnParam(SingleColumnParam singleColumnParam) {
		if (this.singleColumnParams == null) {
			this.singleColumnParams = new LinkedList<>();
		}
		this.singleColumnParams.add(singleColumnParam);
		return this;
	}

	
	@Override
	public Select<T> getSelect() {
		return this;
	}
	

	@Override
	public WhereAble<Select<T>, T> getWhereAble() {
		return this;
	}

	@Override
	public HavingAble<Select<T>, T> getHavingAble() {
		return this;
	}
	
	

	
	//------------------------------------
	
	@Override
	public Having<Select<T>, T> having() {
		this.having = new Having<Select<T>, T>(this);
		return this.having;
	}

	public Map<String, Join<Select<T>, T>> getJoins() {
		return joins;
	}


	
	public EntityInfo getEntityInfo() {
		return entityInfo;
	}

	
	
	public LimitParam getLimit() {
		return limit;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	public void setMainAlias(String mainAlias) {
		this.mainAlias = mainAlias;
	}
	
	public String getMainAlias() {
		return mainAlias;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public List<OrderParam> getOrderParams() {
		return orderParams;
	}
	
	public List<SelectParam> getSelectParams() {
		return selectParams;
	}
	
	public List<GroupByParam> getGroupByParams() {
		return groupByParams;
	}
	
	public Having<Select<T>, T> getHaving() {
		return having;
	}

	public Select<T> getThis() {
		return this;
	}
	
	public void setLimit(LimitParam limitParam) {
		this.limit = limitParam;
	}
	
	public List<EachRowMapParam> getColumnOneToManyParams() {
		return eachRowMapParams;
	}
	
	public void setColumnOneToManyParams(List<EachRowMapParam> eachRowMapParams) {
		this.eachRowMapParams = eachRowMapParams;
	}
	
	public KeyFormatter getKeyFormatter() {
		if (this.keyFormatter == null) {
			this.keyFormatter = new DefaultKeyFormatter();
		}
		return keyFormatter;
	}
	
	public void setKeyFormatter(KeyFormatter keyFormatter) {
		this.keyFormatter = keyFormatter;
	}
	
	public ValueFormatter getValueFormatter() {
		if (this.valueFormatter == null) {
			this.valueFormatter = new DefaultValueFormatter();
		}
		return this.valueFormatter;
	}
	
	public List<SingleColumnParam> getSingleColumnParams() {
		return singleColumnParams;
	}
	
	public void setSingleColumnParams(List<SingleColumnParam> singleColumnParams) {
		this.singleColumnParams = singleColumnParams;
	}
	
	public List<EachRowMapParam> getEachRowMapParams() {
		return eachRowMapParams;
	}
	
	public void setEachRowMapParams(List<EachRowMapParam> eachRowMapParams) {
		this.eachRowMapParams = eachRowMapParams;
	}
	
	public void setValueFormatter(ValueFormatter valueFormatter) {
		this.valueFormatter = valueFormatter;
	}
	
	public void setJdbcLink(JdbcLink jdbcLink) {
		this.jdbcLink = jdbcLink;
	}
	

	@Override
	public Select<T> setLimitParam(LimitParam limitParam) {
		this.limit = limitParam;
		return this;
	}

}
