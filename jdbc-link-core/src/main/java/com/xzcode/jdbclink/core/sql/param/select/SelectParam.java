package com.xzcode.jdbclink.core.sql.param.select;

import com.xzcode.jdbclink.core.sql.Select;

/**
 * select 列参数
 * 
 * 
 * @author zai
 * 2017-05-27
 */
public class SelectParam{
	
	
	protected String column = "";
	
	protected String alias = "";
	
	protected String tableAlias = "";
	
	protected Select<?> select;
	
	public SelectParam() {
	}

	public SelectParam(String column, String alias, Select<?> select) {
		super();
		this.column = column;
		this.alias = alias;
		this.select = select;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		if (column != null) {
			this.column = column;			
		}
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
	

}
