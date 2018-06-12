package com.sourcemuch.commons.jdbclink.core.sql.param.select;

public class EachRowMapParam{
	
	
	protected EachRowMapExecution execution;
	
	public EachRowMapParam() {
	}

	public EachRowMapParam(EachRowMapExecution execution) {
		this.execution = execution;
	}

	public void setExecution(EachRowMapExecution execution) {
		this.execution = execution;
	}
	
	public EachRowMapExecution getExecution() {
		return execution;
	}

}
