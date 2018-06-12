package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.limit.LimitParam;

public interface LimitParamSetAble<T> {
	
	T setLimitParam(LimitParam limitParam);
	
}
