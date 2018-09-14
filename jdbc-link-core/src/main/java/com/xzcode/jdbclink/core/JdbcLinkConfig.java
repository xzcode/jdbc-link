package com.xzcode.jdbclink.core;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.core.annotations.Entity;
import com.xzcode.jdbclink.core.cache.DefaultEntityInfoCache;
import com.xzcode.jdbclink.core.cache.IEntityInfoCache;
import com.xzcode.jdbclink.core.pool.string.StringBuilderPool;
import com.xzcode.jdbclink.core.resolver.ISqlResolver;
import com.xzcode.jdbclink.core.resolver.SqlResolver;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;


public class JdbcLinkConfig{
	
	
	private JdbcTemplate jdbcTemplate;
	
	/**实体类所在包*/
	private String[] entityPackages;
		
	//StringBuilder对象池装配
	private StringBuilderPool stringBuilderPool = new StringBuilderPool();
	
	//实体信息缓存装配
	private IEntityInfoCache entityInfoCache = new DefaultEntityInfoCache();
	
	private ISqlResolver sqlResolver;
	
	
	
	public JdbcLinkConfig(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		init();
	}


	public JdbcLinkConfig(JdbcTemplate jdbcTemplate, String[] entityPackages) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.entityPackages = entityPackages;
		init();
	}


	/**
	 * 初始化配置
	 * 
	 * 
	 * @author zai
	 * 2018-04-21
	 */
	public void init() {
		
		 sqlResolver = new SqlResolver(this);
		
		//扫描实体类
		if (entityPackages != null && entityPackages.length > 0) {
			new FastClasspathScanner(entityPackages)  
		    .matchClassesWithAnnotation(Entity.class, clazz -> {
		    	this.entityInfoCache.addEntityInfo(clazz);
		    })
		    .scan();
		}
		
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}



	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	public StringBuilderPool getStringBuilderPool() {
		return stringBuilderPool;
	}



	public void setStringBuilderPool(StringBuilderPool stringBuilderPool) {
		this.stringBuilderPool = stringBuilderPool;
	}



	public IEntityInfoCache getEntityInfoCache() {
		return entityInfoCache;
	}



	public void setEntityInfoCache(IEntityInfoCache entityInfoCache) {
		this.entityInfoCache = entityInfoCache;
	}
	
	
	public ISqlResolver getSqlResolver() {
		return sqlResolver;
	}
	
	public void setSqlResolver(ISqlResolver sqlResolver) {
		this.sqlResolver = sqlResolver;
	}
	


}
