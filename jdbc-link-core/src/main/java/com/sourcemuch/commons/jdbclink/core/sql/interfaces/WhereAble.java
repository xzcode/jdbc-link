package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.where.Where;

public interface WhereAble<T, C> {
	
	public WhereAble<T, C> getWhereAble();
	
	public default Where<T, C> where(){
		return getWhereAble().where();
	}

	

}
