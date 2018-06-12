package com.sourcemuch.commons.jdbclink.generator.custom.module;

/**
 * 模块包名生成接口
 * 
 * 
 * @author zai
 * 2018-04-16
 */
public interface IModuleNameGenerator {
	
	/**
	 * 生成模块包名
	 * @param tableName 表名
	 * @param splitter 分隔符
	 * @param excludePrefix 排除表名前缀
	 * @return
	 * 
	 * @author zai
	 * 2018-04-18
	 */
	public String generateModulePackageName(String tableName, String splitter ,String[] excludePrefix);

}
