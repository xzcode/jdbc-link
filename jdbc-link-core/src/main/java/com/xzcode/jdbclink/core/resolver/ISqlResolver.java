package com.xzcode.jdbclink.core.resolver;

import com.xzcode.jdbclink.core.models.SqlAndParams;
import com.xzcode.jdbclink.core.pool.string.StringBuilderPool;
import com.xzcode.jdbclink.core.sql.Delete;
import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.update.Update;

public interface ISqlResolver {

	public SqlAndParams handelSelect(Select<?> select);
	
	
	/**
	 * 处理update语句
	 * @param update
	 * @param stringBuilderPool
	 * @return
	 * 
	 * @author zai
	 * 2017-06-13
	 */
	public SqlAndParams handelUpdate(Update update);
	
	
	/**
	 * 处理update语句
	 * @param delete
	 * @param stringBuilderPool
	 * @return
	 * 
	 * @author zai
	 * 2017-06-13
	 */
	public SqlAndParams handelDelete(Delete delete);

}
