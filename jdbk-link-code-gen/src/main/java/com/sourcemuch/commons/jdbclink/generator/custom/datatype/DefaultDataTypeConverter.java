package com.sourcemuch.commons.jdbclink.generator.custom.datatype;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 默认 Mysql 数据库类型 与 java数据类型转换 实现类类
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public class DefaultDataTypeConverter implements IDataTypeConverter {

	@Override
	public Class<?> convert(String type, Integer dataLength, Integer pointLength) {
		
		type = type.toLowerCase();
		
		switch (type) {
		
		case "int":
		case "tinyint":
		case "smallint":
		case "mediumint":
		case "integer":
			return Integer.class;
			
		case "bigint":
			return Long.class;
			
		case "datetime":
			return Date.class;
			
		case "varchar":
			return String.class;
			
		case "decimal":
			return BigDecimal.class;
			
		case "double":
			return Double.class;
			
		case "float":
			return Float.class;

		default:
			return String.class;
		}
	}

	

}
