package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class IntQueryResultSetHandler implements QueryResultSetHandler<Integer> {


	@Override
	public Integer handle(ResultSet rs) throws SQLException {

		if (rs.next()) {
			return  rs.getInt(1);
		}
		return null;
	}
	
	

}
