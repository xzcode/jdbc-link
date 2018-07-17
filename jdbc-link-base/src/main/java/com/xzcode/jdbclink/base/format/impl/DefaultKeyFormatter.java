package com.xzcode.jdbclink.base.format.impl;

import com.xzcode.jdbclink.base.format.KeyFormatter;

public class DefaultKeyFormatter implements KeyFormatter {
	
	/**
	 * 默认列名转换
	 * @param key
	 * @return
	 * 
	 * @author zai
	 * 2017-06-09
	 */
	@Override
	public String format(String key) {
		/*
		String[] strings = key.split("_");
		String result = "";
		for (int i = 0; i < strings.length; i++) {
			result += i > 0 ? StringUtils.capitalize(strings[i]) : strings[i];
		}
		*/
		return key;
	}
	
}
