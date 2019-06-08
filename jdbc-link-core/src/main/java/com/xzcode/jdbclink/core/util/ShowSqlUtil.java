package com.xzcode.jdbclink.core.util;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xzcode.jdbclink.core.models.SqlAndParams;

public class ShowSqlUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShowSqlUtil.class);
	private static final Gson GSON = new GsonBuilder()
			//.setPrettyPrinting()
			.serializeNulls()
			.create();
	
	public static void debugSqlAndParams(SqlAndParams sqlAndParams) {
		
		if (sqlAndParams != null && LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder(100);
			if (sqlAndParams.getSql() != null) {
				sb.append("\nSQL query: ").append(sqlAndParams.getSql());
				sb.append("\nSQL param: ").append(sqlAndParams.getArgs());		
			}
			if (StringUtils.isNotEmpty(sqlAndParams.getCountSql())) {
				sb.append("\nSQL count query: ").append(sqlAndParams.getCountSql());
				sb.append("\nSQL count param: ").append(sqlAndParams.getCountParams());
			}
			if (sqlAndParams.getResults() != null) {
				sb.append("\nSQL result: ").append(GSON.toJson(sqlAndParams.getResults())).append("\n");
			}
			
			LOGGER.debug(sb.toString());
		}
	}
	
	public static void debugSqlAndParams(String sql, Object...params) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nSQL query:{}\nSQL param:{}", sql, params);
		}
	}
	
	public static void debugSqlAndParams(String sql, String params) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nSQL query:{}\nSQL param:{}", sql, params);
		}
	}
	
	public static void debugSqlAndParams(String sql, List<Object> args) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nSQL query:{}\nSQL param:{}", sql, args);
		}
	}
	public static void debugSqlAndParams2(String sql, List<List<Object>> args) {
		if (LOGGER.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder(100);
			sb.append("\nSQL query:").append(sql);
			sb.append("\nSQL param:\n");				
			for (List<Object> list : args) {
				sb.append(list).append("\n");
			}
			LOGGER.debug(sb.toString());
		}
	}

}
