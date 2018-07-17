package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class ObjectQueryResultSetHandler implements QueryResultSetHandler<Object> {


	@Override
	public Object handle(ResultSet rs) throws SQLException {

		if (rs.next()) {
			return  rs.getObject(1);
		}
		return null;
	}
	
	

}
