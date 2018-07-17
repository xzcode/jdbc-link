package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class ListStringQueryResultSetHandler implements QueryResultSetHandler<List<String>> {


	@Override
	public List<String> handle(ResultSet rs) throws SQLException {
		
		boolean last = rs.last();
		if (!last) {
			return null;
		}
		List<String> list = new ArrayList<>(rs.getRow());
		rs.beforeFirst();
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		return list;
	}
	
	

}
