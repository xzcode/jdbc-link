package com.xzcode.jdbclink.core.entity.model;

/**
 * 实体字段属性
 * 
 * 
 * @author zai
 * 2018-09-11 11:10:51
 */
public class EntityField {
	
	private String fieldName;
	
	private String propName;
	
	private String tableAlias;
	
	private String tableName;
	
	
	

	public EntityField(String fieldName, String propName, String tableName) {
		super();
		this.fieldName = fieldName;
		this.tableAlias = tableName;
		this.tableName = tableName;
		this.propName = propName;
	}




	public String fieldName() {
		return fieldName;
	}




	public String propName() {
		return propName;
	}




	public String tableAlias() {
		return tableAlias;
	}


	public String tableName() {
		return tableName;
	}
	
	

}
