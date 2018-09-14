package com.xzcode.jdbclink.core.util;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.jdbclink.core.models.SqlAndParams;

public class ShowSqlUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShowSqlUtil.class);
	
	public static void debugSqlAndParams(SqlAndParams sqlAndParams) {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nSQL query:{} \nSQL param:{}", sqlAndParams.getSql(), sqlAndParams.getArgs());
			if (StringUtils.isNotEmpty(sqlAndParams.getCountSql())) {
				LOGGER.debug("\nSQL query:{} \nSQL param:{}", sqlAndParams.getCountSql(), sqlAndParams.getCountParams());
			}
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

}
