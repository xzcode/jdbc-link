package com.xzcode.jdbclink.core.format;

import org.apache.commons.lang3.StringUtils;

public class DefaultKeyFormatter implements KeyFormatter {
	
	/**
	 * 默认列名转换
	 * @param columnName
	 * @return
	 * 
	 * @author zai
	 * 2017-06-09
	 */
	@Override
	public String format(String columnName) {
		String[] strings = columnName.split("_");
		String result = "";
		for (int i = 0; i < strings.length; i++) {
			result += i > 0 ? StringUtils.capitalize(strings[i]) : strings[i];
		}
		return result;
	}
	
}
