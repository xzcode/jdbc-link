package com.xzcode.jdbclink.codegen.custom.datatype;

/**
 * Mysql 数据库类型 与 java数据类型转换 接口类
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public interface IDataTypeConverter {
	
	/**
	 * 执行类型转换
	 * @param type 数据库类型
	 * @param dataLength 数据长度
	 * @param pointLength 小数点长度
	 * @return 具体java类型的 Class
	 * 
	 * @author zai
	 * 2018-04-17
	 */
	public Class<?> convert(String type, Integer dataLength, Integer pointLength);

}
