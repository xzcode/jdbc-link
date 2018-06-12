package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.Select;

public interface GetSelectAble<T> {
	Select<T> getSelect();
}
