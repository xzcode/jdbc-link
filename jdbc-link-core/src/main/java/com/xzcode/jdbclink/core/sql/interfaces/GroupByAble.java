package com.xzcode.jdbclink.core.sql.interfaces;

import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.sql.groupby.GroupByParam;

public interface GroupByAble<T>{

	T addGroupByParams(GroupByParam groupByParam);
	
	public default T groupBy(EntityField column) {
		return this.addGroupByParams(new GroupByParam(column));
	}
	
	public default T groupBy(String alias, EntityField column) {
		return this.addGroupByParams(new GroupByParam(alias, column));
	}

	
}
