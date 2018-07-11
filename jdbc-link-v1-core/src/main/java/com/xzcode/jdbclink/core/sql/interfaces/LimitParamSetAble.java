package com.xzcode.jdbclink.core.sql.interfaces;

import com.xzcode.jdbclink.core.sql.limit.LimitParam;

public interface LimitParamSetAble<T> {
	
	T setLimitParam(LimitParam limitParam);
	
}
