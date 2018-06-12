package com.sourcemuch.commons.jdbclink.core.sql.param.select.concat;

public class ConcatValue implements IConcatParam{
	
	private String value = "";
	
	
	
	public ConcatValue(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
