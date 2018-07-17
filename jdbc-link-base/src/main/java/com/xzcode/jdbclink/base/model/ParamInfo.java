package com.xzcode.jdbclink.base.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数信息
 * 
 * 
 * @author zai
 * 2018-07-17 12:13:06
 */
public class ParamInfo {
	
	/**
	 * 参数值
	 */
	private Object param;
	
	/**
	 * sql数据类型，具体参照: {@link java.sql.Types}
	 */
	private Integer sqlType;
	
	
	
	
	public ParamInfo(Object param, Integer sqlType) {
		super();
		this.param = param;
		this.sqlType = sqlType;
	}
	
	/**
	 * 便捷实例化方法
	 * @param param
	 * @param sqlType
	 * @return
	 * 
	 * @author zai
	 * 2018-07-17 13:08:26
	 */
	public static ParamInfo create(Object param, Integer sqlType) {
		return new ParamInfo(param, sqlType);
	}
	
	/**
	 * 转换为 List<ParamInfo> 参数信息
	 * @param params 参数数组
	 * @param targetSqlTypes sql数据类型数组
	 * @return
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-17 13:07:19
	 */
	public static List<ParamInfo> toParamInfoList(Object[] params, int[] targetSqlTypes) throws IllegalArgumentException {
		if (params == null || params.length == 0) {
			return null;
		}
		if (targetSqlTypes != null && params.length != targetSqlTypes.length) {
			throw new IllegalArgumentException("SQL params length is inconsistent with targetSqlTypes length!");
		}
		List<ParamInfo> list = new ArrayList<>(params.length);
		ParamInfo pi = null;
		if (targetSqlTypes != null) {
			for (int i = 0; i < params.length; i++) {
				pi = new ParamInfo(params[i], targetSqlTypes[i]);
				list.add(pi);
			}
		}else {
			for (int i = 0; i < params.length; i++) {
				pi = new ParamInfo(params[i], null);
				list.add(pi);
			}
		}
		return list;
	}
	
	
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	public Integer getSqlType() {
		return sqlType;
	}
	public void setSqlType(Integer sqlType) {
		this.sqlType = sqlType;
	}
	
	

}
