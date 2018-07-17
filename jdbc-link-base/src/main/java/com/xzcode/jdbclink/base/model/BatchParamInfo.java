package com.xzcode.jdbclink.base.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量插入参数信息类
 * 
 * 
 * @author zai
 * 2018-07-17 12:13:06
 */
public class BatchParamInfo {
	
	/**
	 * 参数list集合
	 */
	List<ParamInfo> paramInfos;
	
	
	public BatchParamInfo() {
	}
	
	public BatchParamInfo(List<ParamInfo> paramInfos) {
		super();
		this.paramInfos = paramInfos;
	}
	
	public static BatchParamInfo create(List<ParamInfo> paramInfos) {
		if (paramInfos == null) {
			return null;
		}
		return new BatchParamInfo(paramInfos);
		
	}
	
	public static BatchParamInfo create(Object[] params) throws IllegalArgumentException {
		return new BatchParamInfo(ParamInfo.toParamInfoList(params, null));
	}
	
	public static BatchParamInfo create(Object[] params, int[] targetSqlTypes) throws IllegalArgumentException {
		return new BatchParamInfo(ParamInfo.toParamInfoList(params, targetSqlTypes));	
	}

	public List<ParamInfo> getParamInfos() {
		return paramInfos;
	}
	
	public void setParamInfos(List<ParamInfo> paramInfos) {
		this.paramInfos = paramInfos;
	}

}
