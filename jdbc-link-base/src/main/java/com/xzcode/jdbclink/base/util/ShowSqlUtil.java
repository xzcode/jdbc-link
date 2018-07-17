package com.xzcode.jdbclink.base.util;


import java.sql.JDBCType;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.jdbclink.base.model.BatchParamInfo;
import com.xzcode.jdbclink.base.model.ParamInfo;
import com.xzcode.jdbclink.base.models.SqlAndParams;

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
	
	public static void debugSqlAndParams(String sql, List<Object> args) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nSQL query:{}\nSQL params:{}", sql, args);
		}
	}
	
	public static void debugSqlAndParamInfos(String sql, List<ParamInfo> paramInfos) {
		
		if (LOGGER.isDebugEnabled()) {
			StringBuilder args = new StringBuilder();
			if (paramInfos != null) {
				for (ParamInfo pi : paramInfos) {
					args
					.append("[")
					.append(pi.getParam())
					.append("(")
					.append(JDBCType.valueOf(pi.getSqlType()))
					.append(")")
					.append("]")
					.append(",")
					;
					
				}
				args.setLength(args.length() - 1);
			}
			LOGGER.debug("\nSQL query:\n{}\nSQL params:\n{}", sql, args);
		}
	}
	
public static void debugSqlAndBatchParamInfos(String sql, List<BatchParamInfo> batchParamInfos) {
		
		if (LOGGER.isDebugEnabled()) {
			StringBuilder args = new StringBuilder();
			if (batchParamInfos != null) {
				for (BatchParamInfo bpi : batchParamInfos) {
					
					for (ParamInfo pi : bpi.getParamInfos()) {
						args
						.append("[")
						.append(pi.getParam())
						.append("(")
						.append(JDBCType.valueOf(pi.getSqlType()))
						.append(")")
						.append("]")
						.append(",")
						;
					}
					args.setLength(args.length() - 1);
					args.append("\n");
				}
			}
			LOGGER.debug("\nSQL query:\n{}\nSQL params:\n{}", sql, args);
		}
	}

}
