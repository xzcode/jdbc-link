package com.xzcode.jdbclink.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.core.cache.IEntityInfoCache;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.pool.string.StringBuilderPool;
import com.xzcode.jdbclink.core.sql.BatchInsert;
import com.xzcode.jdbclink.core.sql.Delete;
import com.xzcode.jdbclink.core.sql.Insert;
import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.execution.SqlExecution;
import com.xzcode.jdbclink.core.sql.update.Update;

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
		Insert insert = new Insert(clazz, config);
		return insert;
	}
	
	public int insert(IEntity entity) {
		Insert insert = new Insert(entity.getClass(), config);
		return insert.insert(entity, false);
	}
	
	public int insertSelective(IEntity entity) {
		Insert insert = new Insert(entity.getClass(), config);
		return insert.insert(entity, true);
	}
	
	public int insert(IEntity entity, boolean injectAutoIncrementId) {
		Insert insert = new Insert(entity.getClass(), config);
		return insert.insert(entity, injectAutoIncrementId);
	}
	
	
	public void batchInsert(List<IEntity> entities) {
		BatchInsert insert = new BatchInsert(config);
		insert.batchInsert(entities);
	}
	
	public <T> Select<T> select(Class<T> clazz) {
		Select<T> select = new Select<>(clazz, config);
		select.setJdbcLink(this);
		return select;
	}
	
	public <T> Select<T> select(Class<T> clazz, String tableAlias) {
		Select<T> select = new Select<>(clazz, config);
		select.setMainAlias(tableAlias);
		select.setJdbcLink(this);
		return select;
	}
	
	public <T> T select(Object uid, Class<T> clazz) {
		Select<T> select = new Select<>(clazz, config);
		select.setJdbcLink(this);
		EntityInfo entityInfo = entityInfoCache.getEntityInfo(clazz);
		return this.select(clazz).where().and().sqlParam(entityInfo.getPrimaryKeyFieldInfo().getColumn(), " = ", uid).queryEntity();
	}
	
	public <T> T select(EntityField field, Object val, Class<T> clazz) {
		Select<T> select = new Select<>(clazz, config);
		select.setJdbcLink(this);
		return select.byField(field, val);
	}
	
	public List<Map<String, Object>> select(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}
	
	public SqlExecution executeSql(String sql, Object...args) {
		return new SqlExecution(sql, args, jdbcTemplate);
	}
	
	
	public Update update(Class<?> clazz) {
		Update update = new Update(clazz, config);
		return update;
	}
	
	
	public int update(IEntity entity) {
		Update update = new Update(entity.getClass(), config);
		return update.update(entity);
	}
	
	public int updateNullable(IEntity entity, EntityField...nullableFields) {
		Update update = new Update(entity.getClass(), config);
		LinkedList<Object> linkedList = new LinkedList<>();
		linkedList.add(entity);
		return update.updateNullable(entity, nullableFields);
	}
	
	public Delete delete(Class<?> clazz) {
		Delete delete = new Delete(clazz, config);
		return delete;
		
	}
	
	public int delete(Object uid, Class<?> clazz) {
		Delete delete = new Delete(clazz, config);
		return delete.byId(uid);
	}
	
	

}
