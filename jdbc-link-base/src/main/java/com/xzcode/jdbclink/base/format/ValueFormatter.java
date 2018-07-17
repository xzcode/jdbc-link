package com.xzcode.jdbclink.base.format;

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
