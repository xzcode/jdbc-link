package com.sourcemuch.commons.jdbclink.core.sql;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;
import com.sourcemuch.commons.jdbclink.core.cache.IEntityInfoCache;
import com.sourcemuch.commons.jdbclink.core.models.SqlAndParams;
import com.sourcemuch.commons.jdbclink.core.pool.string.StringBuilderPool;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.ExecuteAble;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.WhereAble;
import com.sourcemuch.commons.jdbclink.core.sql.join.Join;
import com.sourcemuch.commons.jdbclink.core.sql.limit.LimitParam;
import com.sourcemuch.commons.jdbclink.core.sql.where.Where;
import com.sourcemuch.commons.jdbclink.core.util.ShowSqlUtil;
import com.sourcemuch.commons.jdbclink.core.util.SqlUtil;

public class Delete extends AbstractCommon implements WhereAble<Delete, Delete>,ExecuteAble{
	
	protected EntityInfo entityInfo;
	
	protected Where<Delete, Delete> where;
	
	protected Map<String, Join<Delete, Delete>> joins;
	
	protected LimitParam limit;
	
	public Delete(Class<?> clazz, JdbcTemplate jdbcTemplate, StringBuilderPool stringBuilderPool, IEntityInfoCache entityInfoCache) {
		this.stringBuilderPool = stringBuilderPool;
		this.jdbcTemplate = jdbcTemplate;
		this.entityInfoCache = entityInfoCache;
		this.entityInfo = entityInfoCache.getEntityInfo(clazz);
		
	}
	
	public int deleteById(Object uid, Class<?> t) {
		EntityInfo entityInfo = entityInfoCache.getEntityInfo(t);
		StringBuilder sb = stringBuilderPool.get();
		sb
		.append(" delete from ")
		.append(entityInfo.getTable())
		.append(" where ")
		.append(entityInfo.getId())
		.append(" = ? ");
		
		;
		String sql = sb.toString();
		ShowSqlUtil.debugSqlAndParams(sql, uid);
		int result = this.jdbcTemplate.update(sql, uid);
		stringBuilderPool.returnOject(sb);
		return result;
	}
	
	
	
	@Override
	public int execute() {
		SqlAndParams sqlAndParams = SqlUtil.handelDelete(this, stringBuilderPool);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		return this.jdbcTemplate.update(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray());
	}
	
	
	public Where<Delete, Delete> where() {
		this.where = new Where<>(this);
		return where;
	}
	
	public  Where<Delete, Delete> getWhere() {
		return where;
	}
	
	/*public DeleteJoin join(Class<?> clazz, String alias) {
		
		DeleteJoin join = new DeleteJoin(this, alias);
		this.addJoins(alias, join);
		join.setJoinTag( "inner join");
		return join;
	}
	
	public DeleteJoin join(Class<?> clazz, String alias, String prefix) {
		
		DeleteJoin join = new DeleteJoin(this, alias, prefix);
		join.setJoinTag( "inner join");
		this.addJoins(alias, join);
		return join;
	}
	
	public DeleteJoin leftJoin(Class<?> clazz, String alias) {
		
		DeleteJoin join = new DeleteJoin(this, alias);
		join.setJoinTag( "left join");
		this.addJoins(alias, join);
		return join;
	}
	
	public DeleteJoin leftJoin(Class<?> clazz, String alias, String prefix) {
		
		DeleteJoin join = new DeleteJoin(this, alias, prefix);
		join.setJoinTag( "left join");
		this.addJoins(alias, join);
		return join;
	}
	
	public DeleteJoin rightJoin(Class<?> clazz, String alias) {
		DeleteJoin join = new DeleteJoin(this, alias);
		join.setJoinTag( "right join");
		this.addJoins(alias, join);
		return join;
	}
	
	public DeleteJoin rightJoin(Class<?> clazz, String alias, String prefix) {
		DeleteJoin join = new DeleteJoin(this, alias, prefix, "right join");
		this.addJoins(alias, join);
		return join;
	}*/
	
	
	@Override
	public ExecuteAble getExecuteAble() {
		return this;
	}
	
	//--------------------------
	
	public EntityInfo getEntityInfo() {
		return entityInfo;
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
	
}
