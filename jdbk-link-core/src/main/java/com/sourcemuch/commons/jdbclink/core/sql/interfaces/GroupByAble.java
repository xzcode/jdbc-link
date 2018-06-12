package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.groupby.GroupByParam;

public interface GroupByAble<T>{

	T addGroupByParams(GroupByParam groupByParam);
	
	public default T groupBy(String column) {
		return this.addGroupByParams(new GroupByParam(column));
	}
	
	public default T groupBy(String alias,String column) {
		return this.addGroupByParams(new GroupByParam(alias, column));
	}

	
}
