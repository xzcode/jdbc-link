package com.xzcode.jdbclink.core.models;

import java.util.List;

public class SqlAndParams {
	
	private String sql;
	private List<Object> args;
	
	private String countSql;
	private List<Object> countParams;
	
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<Object> getArgs() {
		return args;
	}
	public void setArgs(List<Object> args) {
		this.args = args;
	}
	public String getCountSql() {
		return countSql;
	}
	public void setCountSql(String countSql) {
		this.countSql =  countSql ;
	}
	public List<Object> getCountParams() {
		return countParams;
	}
	public void setCountParams(List<Object> countParams) {
		this.countParams = countParams;
	}

	
	
	

}
