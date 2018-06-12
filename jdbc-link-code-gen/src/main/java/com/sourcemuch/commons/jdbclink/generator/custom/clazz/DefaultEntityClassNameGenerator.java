package com.sourcemuch.commons.jdbclink.generator.custom.clazz;

import org.apache.commons.lang3.StringUtils;

/**
 * 实体类属性名生成器
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public class DefaultEntityClassNameGenerator implements IEntityClassNameGenerator{

	@Override
	public String generateClassName(String tableName) {
		String[] strings = tableName.split("_");
		if (strings.length > 0) {
			String name = "";
			for (String s : strings) {
				name += StringUtils.capitalize(s);
			}
			return name;
		}
		return tableName;
	}

	
	
	

}
