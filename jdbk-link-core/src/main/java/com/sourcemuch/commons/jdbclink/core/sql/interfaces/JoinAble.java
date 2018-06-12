package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.cache.IEntityInfoCache;
import com.sourcemuch.commons.jdbclink.core.sql.join.Join;

public interface JoinAble<T, C> {

	void addJoins(String alias, Join<T, C> join);
	
	IEntityInfoCache getEntityInfoCache();
	
	public default Join<T, C> join(Class<?> clazz, String alias) {
		
		Join<T, C> join = new Join<>(getEntityInfoCache().getEntityInfo(clazz),this, alias);
		join.setEntityInfoCache(getEntityInfoCache());
		this.addJoins(alias, join);
		join.setJoinTag( "inner join");
		return join;
	}
	

	public default Join<T, C> join(Class<?> clazz, String alias, String prefix) {
		
		Join<T, C> join = new Join<T, C>(getEntityInfoCache().getEntityInfo(clazz), this, alias, prefix);
		join.setEntityInfoCache(getEntityInfoCache());
		join.setJoinTag( "inner join");
		this.addJoins(alias, join);
		return join;
	}
	
	public default Join<T, C> leftJoin(Class<?> clazz, String alias) {
		
		Join<T, C> join = new Join<T, C>(getEntityInfoCache().getEntityInfo(clazz),this, alias);
		join.setEntityInfoCache(getEntityInfoCache());
		join.setJoinTag( "left join");
		this.addJoins(alias, join);
		return join;
	}
	
	public default Join<T, C> leftJoin(Class<?> clazz, String alias, String prefix) {
		
		Join<T, C> join = new Join<T, C>(getEntityInfoCache().getEntityInfo(clazz),this, alias, prefix);
		join.setEntityInfoCache(getEntityInfoCache());
		join.setJoinTag( "left join");
		this.addJoins(alias, join);
		return join;
	}
	
	public default Join<T, C> rightJoin(Class<?> clazz, String alias) {
		Join<T, C> join = new Join<T, C>(getEntityInfoCache().getEntityInfo(clazz),this, alias);
		join.setEntityInfoCache(getEntityInfoCache());
		join.setJoinTag( "right join");
		this.addJoins(alias, join);
		return join;
	}
	
	public default Join<T, C> rightJoin(Class<?> clazz, String alias, String prefix) {
		Join<T, C> join = new Join<T, C>(getEntityInfoCache().getEntityInfo(clazz),this, alias, prefix, "right join");
		join.setEntityInfoCache(getEntityInfoCache());
		this.addJoins(alias, join);
		return join;
	}
}
