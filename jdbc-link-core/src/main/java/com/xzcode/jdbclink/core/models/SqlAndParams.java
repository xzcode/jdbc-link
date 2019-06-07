package com.xzcode.jdbclink.core.models;

import java.util.ArrayList;
import java.util.List;

public class SqlAndParams {
	
	private String sql;
	private List<Object> args;
	
	private String countSql;
	private List<Object> countParams;
	
	private List<Object> results;
	
	
	
	
	public SqlAndParams() {
	}

	

	public SqlAndParams(String sql, List<Object> args) {
		super();
		this.sql = sql;
		this.args = args;
	}



	public SqlAndParams(String sql, List<Object> args, List<Object> result) {
		super();
		this.sql = sql;
		this.args = args;
		this.results = result;
	}
	
	
	
	public SqlAndParams(String sql, List<Object> args, String countSql, List<Object> countParams) {
		super();
		this.sql = sql;
		this.args = args;
		this.countSql = countSql;
		this.countParams = countParams;
	}

	public void addResult(Object result) {
		if (result == null) {
			result = new ArrayList<>(10);
		}
		this.results.add(result);
	}


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
	public List<Object> getResults() {
		return results;
	}
	public void setResults(List<Object> result) {
		this.results = result;
	}
	
	
	

}
