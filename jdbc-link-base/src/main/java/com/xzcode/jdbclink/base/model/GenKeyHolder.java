package com.xzcode.jdbclink.base.model;

public class GenKeyHolder {
	
	private Number key;
	
	
	
	public GenKeyHolder(Number key) {
		super();
		this.key = key;
	}

	public void setKey(Number key) {
		this.key = key;
	}
	
	public Number getKey() {
		return key;
	}

}
