package com.xzcode.jdbclink.core.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 实体信息类
 * 
 * 
 * @author zai
 * 2018-07-19 22:54:07
 */ 
public class EntityInfo_bk {
	
	private EntityFieldInfo primaryKeyFieldInfo;
	
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
	
	
	

}
