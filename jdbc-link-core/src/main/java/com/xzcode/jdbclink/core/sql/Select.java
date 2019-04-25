package com.xzcode.jdbclink.core.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.xzcode.jdbclink.core.JdbcLink;
import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.format.DefaultKeyFormatter;
import com.xzcode.jdbclink.core.format.DefaultValueFormatter;
import com.xzcode.jdbclink.core.format.KeyFormatter;
import com.xzcode.jdbclink.core.format.ValueFormatter;
import com.xzcode.jdbclink.core.models.Pager;
import com.xzcode.jdbclink.core.models.SqlAndParams;
import com.xzcode.jdbclink.core.resolver.ISqlResolver;
import com.xzcode.jdbclink.core.sql.groupby.GroupByParam;
import com.xzcode.jdbclink.core.sql.groupby.having.Having;
import com.xzcode.jdbclink.core.sql.interfaces.AliasAndPrefix;
import com.xzcode.jdbclink.core.sql.interfaces.GroupByAble;
import com.xzcode.jdbclink.core.sql.interfaces.HavingAble;
import com.xzcode.jdbclink.core.sql.interfaces.JoinAble;
import com.xzcode.jdbclink.core.sql.interfaces.LimitAble;
import com.xzcode.jdbclink.core.sql.interfaces.OrderAble;
import com.xzcode.jdbclink.core.sql.interfaces.QueryAble;
import com.xzcode.jdbclink.core.sql.interfaces.WhereAble;
import com.xzcode.jdbclink.core.sql.join.Join;
import com.xzcode.jdbclink.core.sql.limit.LimitParam;
import com.xzcode.jdbclink.core.sql.order.OrderParam;
import com.xzcode.jdbclink.core.sql.param.select.EachRowMapExecution;
import com.xzcode.jdbclink.core.sql.param.select.EachRowMapParam;
import com.xzcode.jdbclink.core.sql.param.select.SelectParam;
import com.xzcode.jdbclink.core.sql.param.select.SingleClolumnListMapExecution;
import com.xzcode.jdbclink.core.sql.param.select.SingleColumnParam;
import com.xzcode.jdbclink.core.sql.resultset.EntityResultSetExtractor;
import com.xzcode.jdbclink.core.sql.resultset.EntityRowMapper;
import com.xzcode.jdbclink.core.sql.resultset.ListMapRowMapper;
import com.xzcode.jdbclink.core.sql.where.Where;
import com.xzcode.jdbclink.core.util.ParamUtil;
import com.xzcode.jdbclink.core.util.ShowSqlUtil;

public class Select<T> implements 
HavingAble<Select<T>, T>, 
GroupByAble<Select<T>>, 
WhereAble<Select<T>, T>,
JoinAble<Select<T>, T>, 
LimitAble<Select<T>>, 
OrderAble<T>, 
QueryAble<T>, 
AliasAndPrefix {

	protected JdbcLinkConfig config;

	protected String mainAlias = ""; // 主表别名
	
	/**
	 * 数据库名称
	 */
	protected String database;

	private List<EachRowMapParam> eachRowMapParams;

	private List<SingleColumnParam> singleColumnParams;

	private List<SelectParam> selectParams;

	private List<GroupByParam> groupByParams;

	protected EntityInfo entityInfo;

	protected List<OrderParam<T>> orderParams;

	protected LimitParam limit;

	protected String prefix = "";

	protected Class<T> clazz;

	protected Where<Select<T>, T> where;

	protected Having<Select<T>, T> having;

	protected Map<String, Join<Select<T>, T>> joins;

	protected KeyFormatter keyFormatter;

	protected ValueFormatter valueFormatter;

	protected JdbcLink jdbcLink;

	/** 是否生成count sql **/
	protected boolean createCountSql;

	protected ISqlResolver sqlResolver;

	public Select(Class<T> clazz, JdbcLinkConfig config) {
		this.entityInfo = config.getEntityInfoCache().getEntityInfo(clazz);
		this.sqlResolver = config.getSqlResolver();
		this.config = config;
		this.clazz = clazz;
		mainAlias = this.entityInfo.getAlias();
	}
	
	public Select<T> column(String tableAlias, String column){
		return column(tableAlias, column, null);
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

	public Select<T> column(String tableAlias, EntityField field, String alias) {

		column(tableAlias, field.fieldName(), alias);
		return this;
	}

	public Select<T> column(EntityField field, String alias) {
		return this.column(field.tableAlias(), field, alias);
	}

	public Select<T> column(String tableAlias, EntityField field) {
		return this.column(tableAlias, field, null);
	}

	public Select<T> column(EntityField field) {
		return this.column(field.tableAlias(), field, null);
	}

	public Select<T> column(String sqlPart) {

		SelectParam selectParam = new SelectParam();
		selectParam.setType(SelectParam.TypeConstant.SQL_PART);
		selectParam.setSqlPart(sqlPart);
		addSelectParams(selectParam);
		return this;
	}
	

	public Select<T> columnForEach(EachRowMapExecution execution) {

		this.addEachRowMapParam(new EachRowMapParam(execution));

		return this;
	}

	public Select<T> columnForSingleCol(String resultAlias, String outerColumnName, String innerColumnName,
			SingleClolumnListMapExecution execution) {

		this.addSingleColumnParam(new SingleColumnParam(resultAlias, outerColumnName, innerColumnName, execution));
		return this;
	}

	/*
	 * public Select<T> columnForSingleCol( String resultAlias, String
	 * outerColumnName, String innerColumnName, Class<?> innerClazz ) {
	 * 
	 * this.addSingleColumnParam(new SingleColumnParam(resultAlias, outerColumnName,
	 * innerColumnName, new SingleClolumnListMapExecution() {
	 * 
	 * @Override public List<Map<String, Object>> exec(Object[] colArr) { return
	 * jdbcLink.select(innerClazz).where().and().in(innerColumnName,
	 * colArr).queryListMap(); }
	 * 
	 * }));
	 * 
	 * return this; }
	 */
	public Select<T> count(String tableAlias, EntityField field, String alias) {
		return this.column("count(" + tableAlias + "." + field.fieldName() + ") " + alias);
	}

	public Select<T> count(EntityField field, String alias) {
		return this.column("count(" + field.tableAlias() + "." + field.fieldName() + ") " + alias);
	}

	public Select<T> count(EntityField field) {
		return this.column("count(" + field.tableAlias() + "." + field.fieldName() + ") ");
	}

	public Select<T> countAll(String alias) {
		return this.column("count(*) " + alias);
	}

	public Select<T> countAll() {
		return this.column("count(*)");
	}

	/**
	 * sql参数拼接
	 * 
	 * @param sqlPart
	 * @return
	 * 
	 * @author zai 2018-09-11 10:31:45
	 */
	public Select<T> columnSql(Object... sqlPart) {
		return this.column(ParamUtil.getSqlParam(sqlPart));
	}

	public T byField(EntityField field, Object val) {
		return (T) this.where().and().eq(field, val).queryEntity();
	}

	public List<T> byFieldForList(EntityField field, Object val) {
		return this.where().and().eq(field, val).queryList();
	}

	public Map<String, Object> byFieldForMap(EntityField field, Object val) {
		return this.where().and().eq(field, val).queryMap();
	}

	public List<Map<String, Object>> byFieldForListMap(EntityField field, Object val) {
		return this.where().and().eq(field, val).queryListMap();
	}
/*
	@Override
	public OrderParam<T> orderBy(EntityField orderBy) {
		return orderBy(true, orderBy.getTableAlias(), orderBy);
	}
	
	@Override
	public OrderParam<T> orderBy(String tableAlias, EntityField orderBy) {
		return orderBy(true, tableAlias, orderBy);
	}

	@Override
	public OrderParam<T> orderBy(boolean isSactisfy, EntityField orderBy) {
		return orderBy(isSactisfy, orderBy.getTableAlias(), orderBy);
	}
	*/
	@Override
	public OrderParam<T> orderBy(boolean isSactisfy, String tableAlias, EntityField orderBy) {
		OrderParam<T> orderParam = new OrderParam<>(isSactisfy, tableAlias, orderBy, this);
		this.addOrder(orderParam);
		return orderParam;
	}

	@Override
	public Select<T> orderBy(boolean isSactisfy, String tableAlias, String orderBy, String sorting) {
		if (!sorting.equalsIgnoreCase("desc") && !sorting.equalsIgnoreCase("asc")) {
			throw new UnsupportedOperationException("Sorting param must be 'asc' or 'desc' !");
		}
		String lowerCase = orderBy.toLowerCase();
		if (lowerCase.length() > 100) {
			throw new IllegalArgumentException("OrderBy keyword is illeagal : " + orderBy);
		}

		this.addOrder(new OrderParam<T>(isSactisfy, tableAlias, orderBy, sorting, this));
		return this;
	}
/*	
	@Override
	public Select<T> orderBy(String orderBy, String sorting) {
		return orderBy(true, null, orderBy, sorting);
	}

	@Override
	public Select<T> orderBy(String tableAlias, String orderBy, String sorting) {
		return orderBy(true, tableAlias, orderBy, sorting);
	}
	
	*/

	public Select<T> addOrder(OrderParam<T> orderParam) {
		if (orderParam.isSactisfy()) {
			if (this.orderParams == null) {
				this.orderParams = new LinkedList<>();
			}
			this.orderParams.add(orderParam);
		}
		return this;
	}

	public <E> E queryObject(Class<E> ObjectClazz) {
		SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		try {

			return this.config.getJdbcTemplate().queryForObject(sqlAndParams.getSql(), ObjectClazz,
					sqlAndParams.getArgs().toArray());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public T queryEntity() {
		try {
			SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
			ShowSqlUtil.debugSqlAndParams(sqlAndParams);
			return this.config.getJdbcTemplate().query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(),
					new EntityResultSetExtractor<>(clazz, entityInfo));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Pager<T> pageEntity() {
		this.createCountSql = true;
		if (this.limit == null) {
			this.limit = DIFAULT_LIMIT_PARAM;
		}
		SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<T> items = this.config.getJdbcTemplate().query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(),
				new EntityRowMapper<T>(clazz, entityInfo));

		Integer total = config.getJdbcTemplate().queryForObject(sqlAndParams.getCountSql(), Integer.class,
				sqlAndParams.getCountParams());
		Pager<T> pager = new Pager<>();

		pager.setTotal(total);
		pager.setItems(items);
		pager.setPageNo(limit.getPageNo());
		pager.setPageSize(limit.getPageSize());

		return pager;
	}

	public List<T> queryList() {
		SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<T> list = this.config.getJdbcTemplate().query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(),
				new EntityRowMapper<T>(clazz, entityInfo));
		return list;
	}

	public Map<String, Object> queryMap() {

		SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		Map<String, Object> resultMap = config.getJdbcTemplate().query(sqlAndParams.getSql(),
				sqlAndParams.getArgs().toArray(), new ResultSetExtractor<Map<String, Object>>() {

					@Override
					public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<String, Object> map = new LinkedHashMap<>();
						ResultSetMetaData metaData = rs.getMetaData();
						int columnCount = metaData.getColumnCount();
						while (rs.next()) {
							if (map == null) {
								map = new LinkedHashMap<>();
							}
							for (int i = 1; i <= columnCount; i++) {
								map.put(getKeyFormatter().format(metaData.getColumnLabel(i)),
										getValueFormatter().format(rs.getObject(i)));
							}
						}
						return map;
					}
				});

		if (resultMap != null && (this.eachRowMapParams != null || this.singleColumnParams != null)) {
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
	public <F> List<F> queryFirstColumn(Class<F> f) {
		SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<F> resultList = config.getJdbcTemplate().query(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray(),
				new ResultSetExtractor<List<F>>() {

					@SuppressWarnings("unchecked")
					@Override
					public List<F> extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<F> list = new LinkedList<>();

						while (rs.next()) {
							if (f == String.class) {
								list.add((F) String.valueOf(getValueFormatter().format(rs.getObject(1))));
							} else {
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
		SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<Map<String, Object>> resultListMap = config.getJdbcTemplate().query(sqlAndParams.getSql(),
				sqlAndParams.getArgs().toArray(), new ListMapRowMapper(getKeyFormatter(), getValueFormatter()));

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

							Object outId = null;

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
										List<Map<String, Object>> subList2 = (List<Map<String, Object>>) outMap
												.get(param.getResultKeyAlias());
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
		this.createCountSql = true;
		if (this.limit == null) {
			this.limit = DIFAULT_LIMIT_PARAM;
		}
		SqlAndParams sqlAndParams = sqlResolver.handelSelect(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		List<Map<String, Object>> items = config.getJdbcTemplate().query(sqlAndParams.getSql(),
				sqlAndParams.getArgs().toArray(), new ListMapRowMapper(getKeyFormatter(), getValueFormatter()));

		handleColumnExecution(items);

		Integer total = config.getJdbcTemplate().queryForObject(sqlAndParams.getCountSql(),
				sqlAndParams.getCountParams().toArray(), new RowMapper<Integer>() {
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

	public void addSelectParams(SelectParam selectParam) {
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

	@Override
	public Select<T> setLimitParam(LimitParam limitParam) {
		this.limit = limitParam;
		return this;
	}

	@Override
	public Having<Select<T>, T> having() {
		this.having = new Having<Select<T>, T>(this);
		return this.having;
	}

	// ------------------------------------

	public Map<String, Join<Select<T>, T>> getJoins() {
		return joins;
	}

	public EntityInfo getEntityInfo() {
		return entityInfo;
	}

	public LimitParam getLimit() {
		return limit;
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

	public List<OrderParam<T>> getOrderParams() {
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

	public boolean isCreateCountSql() {
		return createCountSql;
	}

	public void setCreateCountSql(boolean createCountSql) {
		this.createCountSql = createCountSql;
	}

	public void setSqlResolver(ISqlResolver sqlResolver) {
		this.sqlResolver = sqlResolver;
	}

	public ISqlResolver getSqlResolver() {
		return sqlResolver;
	}

	@Override
	public JdbcLinkConfig getConfig() {
		return config;
	}

	public void setConfig(JdbcLinkConfig config) {
		this.config = config;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}

}
