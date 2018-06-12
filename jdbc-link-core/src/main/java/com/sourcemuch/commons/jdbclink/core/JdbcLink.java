package com.sourcemuch.commons.jdbclink.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sourcemuch.commons.jdbclink.core.cache.IEntityInfoCache;
import com.sourcemuch.commons.jdbclink.core.pool.string.StringBuilderPool;
import com.sourcemuch.commons.jdbclink.core.sql.BatchInsert;
import com.sourcemuch.commons.jdbclink.core.sql.Delete;
import com.sourcemuch.commons.jdbclink.core.sql.Insert;
import com.sourcemuch.commons.jdbclink.core.sql.Select;
import com.sourcemuch.commons.jdbclink.core.sql.execution.SqlExecution;
import com.sourcemuch.commons.jdbclink.core.sql.update.Update;

public class JdbcLink{
	
	private JdbcLinkConfig config;
	
	//需要 spring jdbc 支持 
	private JdbcTemplate jdbcTemplate;
	
	private StringBuilderPool stringBuilderPool;
	
	private IEntityInfoCache entityInfoCache;
	
	
	
	
	
	public JdbcLink(JdbcLinkConfig config) {
		super();
		setConfig(config);
		
	}


	/**
	 * 添加实体缓存
	 * @param clazz
	 * 
	 * @author zai
	 * 2017-07-25
	 */
	public void addEntityInfo(Class<?> clazz) {
		this.entityInfoCache.addEntityInfo(clazz);
	}
	
	
	public void setConfig(JdbcLinkConfig config) {
		this.config = config;
		this.jdbcTemplate = config.getJdbcTemplate();
		this.stringBuilderPool = config.getStringBuilderPool();
		this.entityInfoCache = config.getEntityInfoCache();
	}
	
	public JdbcLinkConfig getConfig() {
		return config;
	}
	
	
	public Insert insert(Class<?> clazz) {
		Insert insert = new Insert(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		return insert;
	}
	
	public int insert(Object entity) {
		Insert insert = new Insert(entity.getClass(), jdbcTemplate, stringBuilderPool, entityInfoCache);
		return insert.insert(entity, false);
	}
	
	public int insertSelective(Object entity) {
		Insert insert = new Insert(entity.getClass(), jdbcTemplate, stringBuilderPool, entityInfoCache);
		return insert.insert(entity, true);
	}
	
	public int insert(Object entity, boolean injectAutoIncrementId) {
		Insert insert = new Insert(entity.getClass(), jdbcTemplate, stringBuilderPool, entityInfoCache);
		return insert.insert(entity, injectAutoIncrementId);
	}
	
	
	public int batchInsert(List<?> entitys) {
		BatchInsert insert = new BatchInsert(jdbcTemplate, stringBuilderPool, entityInfoCache);
		return insert.batchInsert(entitys, false);
	}
	
	public int batchInsertSelective(List<?> entitys) {
		BatchInsert insert = new BatchInsert(jdbcTemplate, stringBuilderPool, entityInfoCache);
		return insert.batchInsert(entitys, true);
	}
	
	public int batchInsert(List<Object> entitys, boolean injectAutoIncrementId) {
		BatchInsert insert = new BatchInsert(jdbcTemplate, stringBuilderPool, entityInfoCache);
		return insert.batchInsert(entitys, injectAutoIncrementId);
	}
	
	
	public <T> Select<T> select(Class<T> clazz) {
		Select<T> select = new Select<>(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		select.setJdbcLink(this);
		return select;
	}
	
	public <T> Select<T> select(Class<T> clazz, String tableAlias) {
		Select<T> select = new Select<>(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		select.setMainAlias(tableAlias);
		select.setJdbcLink(this);
		return select;
	}
	
	public <T> T select(Object uid, Class<T> clazz) {
		Select<T> select = new Select<>(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		select.setJdbcLink(this);
		return select.selectById(uid);
	}
	
	public <T> T select(String key, Object val, Class<T> clazz) {
		Select<T> select = new Select<>(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		select.setJdbcLink(this);
		return select.selectByKey(key, val);
	}
	
	public List<Map<String, Object>> select(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}
	
	public SqlExecution executeSql(String sql, Object...args) {
		return new SqlExecution(sql, args, jdbcTemplate);
	}
	
	
	public Update update(Class<?> clazz) {
		Update update = new Update(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		return update;
	}
	
	public int update(Object entity) {
		Update update = new Update(entity.getClass(), jdbcTemplate, stringBuilderPool, entityInfoCache);
		return update.update(entity);
	}
	
	
	public int updateNullable(Object entity, String...nullableColumns) {
		Update update = new Update(entity.getClass(), jdbcTemplate, stringBuilderPool, entityInfoCache);
		LinkedList<Object> linkedList = new LinkedList<>();
		linkedList.add(entity);
		return update.updateNullable(entity, nullableColumns);
	}
	
	
	public Delete delete(Class<?> clazz) {
		Delete delete = new Delete(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		return delete;
		
	}
	
	public int delete(Object uid, Class<?> clazz) {
		Delete delete = new Delete(clazz, jdbcTemplate, stringBuilderPool, entityInfoCache);
		return delete.deleteById(uid, clazz);
		
	}
	
	

}
