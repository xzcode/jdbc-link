package com.sourcemuch.commons.jdbclink.core.sql.param.select;

import com.sourcemuch.commons.jdbclink.core.sql.Select;

/**
 * 一对多列参数
 * 
 * @author zai
 * 2018-02-02
 */
public class ColumnOneToManyParam{
	
	
	/**
	 * 关联字段id
	 */
	protected String relatePropName = ""; 
	
	protected String resultKeyAlias  = "" ;
	
	protected Select<?> select;
	
	protected Select<?> subSelect;
	
	public ColumnOneToManyParam() {
	}

	public ColumnOneToManyParam(String relatePropName, String resultKeyAlias, Select<?> subSelect,Select<?> select) {
		super();
		
		this.relatePropName = relatePropName;
		
		this.select = select;
		
		this.subSelect = subSelect;
		
	}
	
	public String getRelatePropName() {
		return relatePropName;
	}
	
	public void setRelatePropName(String relatePropName) {
		if (relatePropName != null) {
			this.relatePropName = relatePropName;			
		}
	}


	public String getResultKeyAlias() {
		return resultKeyAlias;
	}

	public void setResultKeyAlias(String resultKeyAlias) {
		if (resultKeyAlias != null) {
			this.resultKeyAlias = resultKeyAlias;			
		}
	}

	public Select<?> getSelect() {
		return select;
	}

	public void setSelect(Select<?> select) {
		this.select = select;
	}

	public Select<?> getSubSelect() {
		return subSelect;
	}
	
	public void setSubSelect(Select<?> subSelect) {
		this.subSelect = subSelect;
	}
	
	

}
