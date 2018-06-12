package com.sourcemuch.commons.jdbclink.core.format;

/**
 * 列名格式转换器
 * 
 * 
 * @author zai
 * 2017-06-09
 */
public interface ValueFormatter {

	
	Object format(Object value);

}
