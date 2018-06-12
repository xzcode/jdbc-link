package com.xzcode.jdbclink.core.sql.interfaces;

import com.xzcode.jdbclink.core.sql.Select;

public interface GetSelectAble<T> {
	Select<T> getSelect();
}
