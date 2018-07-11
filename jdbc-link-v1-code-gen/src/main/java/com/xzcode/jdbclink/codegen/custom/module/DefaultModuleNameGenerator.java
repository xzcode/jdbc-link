package com.xzcode.jdbclink.codegen.custom.module;

/**
 * 默认模块包名生成器
 * <br/>
 * 如：user_info 表，将获取user作为模块包名
 * 
 * 
 * @author zai
 * 2018-04-16
 */
public class DefaultModuleNameGenerator implements IModuleNameGenerator {

	@Override
	public String generateModulePackageName(String tableName, String splitter ,String[] excludePrefix) {
		if (splitter == null) {
			splitter = "_";
		}
		if (excludePrefix != null && excludePrefix.length > 0) {
			for (String string : excludePrefix) {
				if(tableName.startsWith(tableName)) {
					tableName = tableName.replaceFirst(string, "");
					break;
				}
			}
		}
		String[] strings = tableName.split(splitter);
		
		return strings[0];
	}


}
