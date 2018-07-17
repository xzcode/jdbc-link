package com.xzcode.jdbclink.base.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryResultSetHandler<T> {
	
	T handle(ResultSet rs) throws SQLException;
	
}
