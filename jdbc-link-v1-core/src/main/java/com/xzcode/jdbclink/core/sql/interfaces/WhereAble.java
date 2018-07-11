package com.xzcode.jdbclink.core.sql.interfaces;

import com.xzcode.jdbclink.core.sql.where.Where;

public interface WhereAble<T, C> {
	
	public WhereAble<T, C> getWhereAble();
	
	public default Where<T, C> where(){
		return getWhereAble().where();
	}

	

}
