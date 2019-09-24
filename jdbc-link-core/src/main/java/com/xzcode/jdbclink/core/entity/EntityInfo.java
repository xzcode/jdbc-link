package com.xzcode.jdbclink.core.entity;

import java.util.List;

public class EntityInfo {
	
private EntityFieldInfo primaryKeyFieldInfo;
	
	/*private String database;*/
	
	private String table;
	
	private String alias;
	
	private Class<?> clazz;
	
	List<EntityFieldInfo> fieldInfos;
	
	public void setPrimaryKeyFieldInfo(EntityFieldInfo primaryKeyFieldInfo) {
		this.primaryKeyFieldInfo = primaryKeyFieldInfo;
	}
	
	public EntityFieldInfo getPrimaryKeyFieldInfo() {
		return primaryKeyFieldInfo;
	}
	
	

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}


	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<EntityFieldInfo> getFieldInfos() {
		return fieldInfos;
	}
	
	public void setFieldInfos(List<EntityFieldInfo> fieldInfos) {
		this.fieldInfos = fieldInfos;
	}

	/*public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
*/
}
