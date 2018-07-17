package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class ShortQueryResultSetHandler implements QueryResultSetHandler<Short> {


	@Override
	public Short handle(ResultSet rs) throws SQLException {

		if (rs.next()) {
			return  rs.getShort(1);
		}
		return null;
	}
	
	

}
