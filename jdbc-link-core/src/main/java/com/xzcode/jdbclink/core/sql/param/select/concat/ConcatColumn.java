package com.xzcode.jdbclink.core.sql.param.select.concat;

public class ConcatColumn implements IConcatParam{
	
	protected String column = "";
	
	protected String tableAlias = "";
	
	public ConcatColumn(String tableAlias, String column) {
		this.column = column;
		this.tableAlias = tableAlias;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	
	
}
