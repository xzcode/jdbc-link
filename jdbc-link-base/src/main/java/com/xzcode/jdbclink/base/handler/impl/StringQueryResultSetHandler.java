package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class StringQueryResultSetHandler implements QueryResultSetHandler<String> {


	@Override
	public String handle(ResultSet rs) throws SQLException {

		if (rs.next()) {
			return  rs.getString(1);
		}
		return null;
	}
	
	

}
