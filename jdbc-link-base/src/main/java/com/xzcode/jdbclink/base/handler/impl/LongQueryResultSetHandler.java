package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class LongQueryResultSetHandler implements QueryResultSetHandler<Long> {


	@Override
	public Long handle(ResultSet rs) throws SQLException {

		if (rs.next()) {
			return  rs.getLong(1);
		}
		return null;
	}
	
	

}
