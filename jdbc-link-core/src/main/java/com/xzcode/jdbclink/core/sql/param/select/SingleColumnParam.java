package com.xzcode.jdbclink.core.sql.param.select;

public class SingleColumnParam{
	
	protected String resultKeyAlias  = "" ;
	
	protected String outerColumnName  = "" ;
	
	protected String innerColumnName  = "" ;
	
	protected SingleClolumnListMapExecution execution;
	
	public SingleColumnParam() {
	}


	public SingleColumnParam(String resultKeyAlias, String outerColumnName, String innerColumnName,
			SingleClolumnListMapExecution execution) {
		super();
		if (resultKeyAlias != null) {
			this.resultKeyAlias = resultKeyAlias;			
		}
		
		if (outerColumnName != null) {
			this.outerColumnName = outerColumnName;
		}
		
		if (innerColumnName != null) {
			this.innerColumnName = innerColumnName;
		}
		
		this.execution = execution;
	}

	public String getResultKeyAlias() {
		return resultKeyAlias;
	}

	public void setResultKeyAlias(String resultKeyAlias) {
		if (resultKeyAlias != null) {
			this.resultKeyAlias = resultKeyAlias;			
		}
	}
	
	public String getInnerColumnName() {
		return innerColumnName;
	}
	
	public void setInnerColumnName(String innerColumnName) {
		if (innerColumnName != null) {
			this.innerColumnName = innerColumnName;			
		}
	}
	
	public String getOuterColumnName() {
		return outerColumnName;
	}
	
	public void setOuterColumnName(String outerColumnName) {
		if (outerColumnName != null) {
			this.outerColumnName = outerColumnName;			
		}
	}
	
	public void setExecution(SingleClolumnListMapExecution execution) {
		this.execution = execution;
	}
	
	public SingleClolumnListMapExecution getExecution() {
		return execution;
	}

}
