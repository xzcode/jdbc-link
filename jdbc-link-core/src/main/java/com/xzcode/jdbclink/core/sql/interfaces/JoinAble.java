package com.xzcode.jdbclink.core.sql.interfaces;

import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.sql.join.Join;


public interface JoinAble<T, C> {

	void addJoins(String alias, Join<T, C> join);
	
	JdbcLinkConfig getConfig();
	
	public default Join<T, C> join(Class<?> clazz, String alias) {
		EntityInfo entityInfo = getConfig().getEntityInfoCache().getEntityInfo(clazz);
		Join<T, C> join = new Join<>(getConfig(), entityInfo, this, alias);
		this.addJoins(getAlias(alias, entityInfo), join);
		join.setJoinTag( "inner join");
		return join;
	}
	

	public default Join<T, C> join(Class<?> clazz, String alias, String prefix) {
		EntityInfo entityInfo = getConfig().getEntityInfoCache().getEntityInfo(clazz);
		Join<T, C> join = new Join<T, C>(getConfig(), entityInfo, this, alias, prefix);
		join.setJoinTag( "inner join");
		this.addJoins(getAlias(alias, entityInfo), join);
		return join;
	}
	
	public default Join<T, C> leftJoin(Class<?> clazz) {
		EntityInfo entityInfo = getConfig().getEntityInfoCache().getEntityInfo(clazz);
		Join<T, C> join = new Join<T, C>(getConfig(), entityInfo, this, null);
		join.setJoinTag( "left join");
		this.addJoins(getAlias(null, entityInfo), join);
		return join;
	}
	
	public default Join<T, C> leftJoin(Class<?> clazz, String alias) {
		EntityInfo entityInfo = getConfig().getEntityInfoCache().getEntityInfo(clazz);
		Join<T, C> join = new Join<T, C>(getConfig(), entityInfo, this, alias);
		join.setJoinTag( "left join");
		this.addJoins(getAlias(alias, entityInfo), join);
		return join;
	}
	
	public default Join<T, C> leftJoin(Class<?> clazz, String alias, String prefix) {
		EntityInfo entityInfo = getConfig().getEntityInfoCache().getEntityInfo(clazz);
		Join<T, C> join = new Join<T, C>(getConfig(), entityInfo,this, alias, prefix);
		join.setJoinTag( "left join");
		this.addJoins(getAlias(alias, entityInfo), join);
		return join;
	}
	
	public default Join<T, C> rightJoin(Class<?> clazz, String alias) {
		EntityInfo entityInfo = getConfig().getEntityInfoCache().getEntityInfo(clazz);
		Join<T, C> join = new Join<T, C>(getConfig(), entityInfo,this, alias);
		join.setJoinTag( "right join");
		this.addJoins(getAlias(alias, entityInfo), join);
		return join;
	}
	
	public default Join<T, C> rightJoin(Class<?> clazz, String alias, String prefix) {
		EntityInfo entityInfo = getConfig().getEntityInfoCache().getEntityInfo(clazz);
		Join<T, C> join = new Join<T, C>(getConfig(), entityInfo, this, alias, prefix, "right join");
		this.addJoins(getAlias(alias, entityInfo), join);
		return join;
	}
	
	public default String getAlias(String alias, EntityInfo entityInfo) {
			if (alias == null || alias.equals("")) {
				return entityInfo.getAlias();
			}
			return alias;
	}
}
