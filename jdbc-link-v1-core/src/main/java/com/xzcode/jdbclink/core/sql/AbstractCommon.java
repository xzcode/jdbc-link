package com.xzcode.jdbclink.core.sql;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.core.cache.IEntityInfoCache;
import com.xzcode.jdbclink.core.pool.string.StringBuilderPool;

public abstract class AbstractCommon {
	
	protected StringBuilderPool stringBuilderPool;
	
	//需要 spring jdbc 支持 
	protected JdbcTemplate jdbcTemplate;
	
	protected IEntityInfoCache entityInfoCache;

	public StringBuilderPool getStringBuilderPool() {
		return stringBuilderPool;
	}

	public void setStringBuilderPool(StringBuilderPool stringBuilderPool) {
		this.stringBuilderPool = stringBuilderPool;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public IEntityInfoCache getEntityInfoCache() {
		return entityInfoCache;
	}

	public void setEntityInfoCache(IEntityInfoCache entityInfoCache) {
		this.entityInfoCache = entityInfoCache;
	}
	
	
	
	
	

}
