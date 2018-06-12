package com.sourcemuch.commons.jdbclink.generator.data.adapter;

import java.util.List;

import com.sourcemuch.commons.jdbclink.generator.model.ColumnInfo;
import com.sourcemuch.commons.jdbclink.generator.model.TableEntityInfo;

public interface IDatabaseInfoAdapter {
	
	

	/**
	 * 获取所有数据库
	 * @return
	 * 
	 * @author zai
	 * 2018-04-14
	 */
	public List<String> getDatabases();

	/**
	 * 获取库中所有表
	 * @param databaseName
	 * @return
	 * 
	 * @author zai
	 * 2018-04-14
	 */
	public List<TableEntityInfo> getTableInfos(String databaseName);

	/**
	 * 获取列信息
	 * @param databaseName
	 * @param tableName
	 * @return
	 * 
	 * @author zai
	 * 2018-04-14
	 */
	public List<ColumnInfo> getColumnsInfo(String databaseName, String tableName) ;

	/**
	 * 获取建表语句
	 * @param databaseName
	 * @param tableName
	 * @return
	 * 
	 * @author zai
	 * 2018-04-14
	 */
	public String getCreateTable(String databaseName, String tableName) ;

}
