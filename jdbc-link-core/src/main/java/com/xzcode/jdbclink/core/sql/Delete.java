package com.xzcode.jdbclink.core.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.models.SqlAndParams;
import com.xzcode.jdbclink.core.resolver.ISqlResolver;
import com.xzcode.jdbclink.core.sql.interfaces.ExecuteAble;
import com.xzcode.jdbclink.core.sql.interfaces.WhereAble;
import com.xzcode.jdbclink.core.sql.join.Join;
import com.xzcode.jdbclink.core.sql.limit.LimitParam;
import com.xzcode.jdbclink.core.sql.where.Where;
import com.xzcode.jdbclink.core.util.ShowSqlUtil;

public class Delete implements WhereAble<Delete, Delete>, ExecuteAble{
	
	protected JdbcLinkConfig config;
	
	protected EntityInfo entityInfo;
	
	protected Where<Delete, Delete> where;
	
	protected Map<String, Join<Delete, Delete>> joins;
	
	protected ISqlResolver sqlResolver;
	
	protected LimitParam limit;
	
	protected String database;
	
	public Delete(Class<?> clazz, JdbcLinkConfig config) {
		this.sqlResolver = config.getSqlResolver();
		this.config = config;
		this.entityInfo = config.getEntityInfoCache().getEntityInfo(clazz);
		
	}
	
	public int byId(Object uid) {
		StringBuilder sb = config.getStringBuilderPool().get();
		sb
		.append(" delete from ");
		if(StringUtils.isNotEmpty(this.database)) {
			sb.append(this.database).append(".");
		}
		sb.append(entityInfo.getTable());
		sb.append(" where ")
		.append(entityInfo.getPrimaryKeyFieldInfo().getColumn())
		.append(" = ? ");
		;
		String sql = sb.toString();
		config.getStringBuilderPool().returnOject(sb);
		ShowSqlUtil.debugSqlAndParams(sql, uid);
		return this.config.getJdbcTemplate().update(sql, uid);
	}
	
	public int byField(EntityField field, Object value) {
		StringBuilder sb = config.getStringBuilderPool().get();
		sb
		.append(" delete from ");
		if(StringUtils.isNotEmpty(this.database)) {
			sb.append(this.database).append(".");
		}
		sb.append(entityInfo.getTable())
		.append(" where ")
		.append(field.fieldName())
		.append(" = ? ");
		
		;
		String sql = sb.toString();
		config.getStringBuilderPool().returnOject(sb);
		ShowSqlUtil.debugSqlAndParams(sql, value);
		return this.config.getJdbcTemplate().update(sql, value);
	}
	
	
	
	@Override
	public int execute() {
		SqlAndParams sqlAndParams = sqlResolver.handelDelete(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		return this.config.getJdbcTemplate().update(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray());
	}
	
	
	public Where<Delete, Delete> where() {
		this.where = new Where<>(this);
		return where;
	}
	
	public  Where<Delete, Delete> getWhere() {
		return where;
	}
	
	
	@Override
	public ExecuteAble getExecuteAble() {
		return this;
	}
	
	//--------------------------
	
	public EntityInfo getEntityInfo() {
		return entityInfo;
	}
	
	public ISqlResolver getSqlResolver() {
		return sqlResolver;
	}
	
	public void setSqlResolver(ISqlResolver sqlResolver) {
		this.sqlResolver = sqlResolver;
	}
	
	@Override
	public WhereAble<Delete, Delete> getWhereAble() {
		return this;
	}
	
	public void setLimit(LimitParam limit) {
		this.limit = limit;
	}

	public LimitParam getLimit() {
		return this.limit;
	}
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
}
