package com.xzcode.jdbclink.base.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 批量insert结果处理器 接口
 * @param <T>
 * 
 * @author zai
 * 2018-07-17 16:12:05
 */
public interface BatchInsertResultSetHandler<T> {
	
	T handle(int[] updateRows, ResultSet genKeyRs) throws SQLException;

}
