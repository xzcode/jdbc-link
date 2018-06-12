package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

public interface ExecuteAble {
	
	public ExecuteAble getExecuteAble();;
	
	
	public default int execute(){
		return getExecuteAble().execute();
	}
 

}
