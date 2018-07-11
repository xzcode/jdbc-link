package com.xzcode.jdbclink.core.format;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 默认值格式转化
 * 
 * 
 * @author zai
 * 2017-06-09
 */
public class DefaultValueFormatter implements ValueFormatter {

	@Override
	public Object format(Object value) {
		
		//时间格式转化, Timestamp --> Date
		if (value instanceof Timestamp) {
			return new Date(((Timestamp)value).getTime());
		}
		
		/*if (value instanceof Date) {
			return new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss").format(value);
		}*/
		
		return value;
	}

}
