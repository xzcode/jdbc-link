package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class ArrayQueryResultSetHandler implements QueryResultSetHandler<Object[]> {


	@Override
	public Object[] handle(ResultSet rs) throws SQLException {
		boolean last = rs.last();
		if (!last) {
			return null;
		}
		Object[] arr = new Object[rs.getRow()];
		rs.beforeFirst();
		int i = 0;
		while (rs.next()) {
			arr[i++] = rs.getObject(1);
		}
		return arr;
	}
	
	

}
