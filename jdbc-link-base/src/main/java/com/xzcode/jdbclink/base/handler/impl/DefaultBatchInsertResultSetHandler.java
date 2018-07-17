package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.xzcode.jdbclink.base.handler.BatchInsertResultSetHandler;

/**
 * 批量insert结果处理器 默认实现
 * 
 * 
 * @author zai
 * 2018-07-17 16:13:10
 */
public class DefaultBatchInsertResultSetHandler implements BatchInsertResultSetHandler<int[]> {

	@Override
	public int[] handle(int[] updateRows, ResultSet genKeyRs) throws SQLException {
		return updateRows;
	}

}
