package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.limit.LimitParam;

public interface LimitSizeAble<T> extends LimitParamSetAble<T>{
	
	public default T limit(Integer size) {
		return setLimitParam(new LimitParam(0, 1, size));
	}
	
}
