package com.xzcode.jdbclink.core.sql.param.select;

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
