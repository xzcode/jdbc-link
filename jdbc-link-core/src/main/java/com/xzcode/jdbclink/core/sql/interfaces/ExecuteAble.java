package com.xzcode.jdbclink.core.sql.interfaces;

public interface ExecuteAble {
	
	public ExecuteAble getExecuteAble();;
	
	
	public default int execute(){
		return getExecuteAble().execute();
	}
 

}
