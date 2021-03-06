package com.xzcode.jdbclink.core.sql.param.select;

import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.sql.Select;

/**
 * select 列参数
 * 
 * 
 * @author zai
 * 2017-05-27
 */
public class SelectParam{
	
	
	
	protected String column = null;
	
	protected String sqlPart = null;
	
	protected String alias = "";
	
	protected String tableAlias = "";
	
	protected Select<?> select;
	
	protected int type = 1; //参数类型，1 DEFAULT， 2 sqlpart
	
	/**
	 * 参数类型常量
	 * 
	 * @author zai
	 * 2018-09-10 17:20:57
	 */
	public static interface TypeConstant{
		
		int DEFAULT = 1;
		
		int SQL_PART = 2;
	}
	
	public SelectParam() {
	}

	public SelectParam(String tableAlias,String field, String alias, Select<?> select) {
		super();
		this.column = field;
		this.alias = alias;
		this.select = select;
		setTableAlias(tableAlias);
	}

	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		if (alias != null) {
			this.alias = alias;
		}
	}

	public Select<?> getSelect() {
		return select;
	}

	public void setSelect(Select<?> select) {
		this.select = select;
	}
	
	public String getTableAlias() {
		return tableAlias;
	}
	
	public void setTableAlias(String tableAlias) {
		if (tableAlias != null) {
			this.tableAlias = tableAlias;
		}
	}
	
	public String getSqlPart() {
		return sqlPart;
	}
	
	public void setSqlPart(String sqlPart) {
		this.sqlPart = sqlPart;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
