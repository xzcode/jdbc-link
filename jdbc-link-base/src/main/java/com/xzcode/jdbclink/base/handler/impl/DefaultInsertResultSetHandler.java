package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.xzcode.jdbclink.base.handler.InsertResultSetHandler;

/**
 * 默认insert结果处理实现
 * 
 * 
 * @author zai
 * 2018-07-17 16:10:38
 */
public class DefaultInsertResultSetHandler implements InsertResultSetHandler<Integer> {

	@Override
	public Integer handle(int updateRows, ResultSet genKeyRs) throws SQLException {
		return updateRows;
	}

}
