package com.xzcode.jdbclink.codegen.custom.clazz;

/**
 * 实体类 java 类名生成器
 * 
 * 
 * @author zai
 * 2018-04-18
 */
public interface IEntityClassNameGenerator {
	
	public String generateClassName(String tableName);

}
