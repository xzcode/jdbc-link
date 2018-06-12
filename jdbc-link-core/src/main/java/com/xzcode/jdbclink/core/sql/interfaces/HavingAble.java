package com.xzcode.jdbclink.core.sql.interfaces;

import com.xzcode.jdbclink.core.sql.groupby.having.Having;

public interface HavingAble<T, C> {
	
	public HavingAble<T, C> getHavingAble();
	
	public default Having<T, C> having(){
		return getHavingAble().having();
	}

	

}
