package com.xzcode.jdbclink.core;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.core.cache.DefaultEntityInfoCache;
import com.xzcode.jdbclink.core.cache.IEntityInfoCache;
import com.xzcode.jdbclink.core.pool.string.StringBuilderPool;
import com.xzcode.jdbclink.core.resolver.ISqlResolver;
import com.xzcode.jdbclink.core.resolver.SqlResolver;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;


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
			ScanResult scanResult = new ClassGraph()
			//.verbose()                   // Log to stderr
            .enableAllInfo()             // Scan classes, methods, fields, annotations
            .whitelistPackages(entityPackages)
            .scan()
            ;
			ClassInfoList classInfoList = scanResult.getClassesWithAnnotation("com.xzcode.jdbclink.core.annotations.Entity");
			for (ClassInfo classInfo : classInfoList) {
				
				this.entityInfoCache.addEntityInfo(classInfo.loadClass());
				
			}
			
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
