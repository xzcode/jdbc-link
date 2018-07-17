package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class ListQueryResultSetHandler implements QueryResultSetHandler<List<Object>> {


	@Override
	public List<Object> handle(ResultSet rs) throws SQLException {
		
		boolean last = rs.last();
		if (!last) {
			return null;
		}
		List<Object> list = new ArrayList<>(rs.getRow());
		rs.beforeFirst();
		while (rs.next()) {
			list.add(rs.getObject(1));
		}
		return list;
	}
	
	

}
