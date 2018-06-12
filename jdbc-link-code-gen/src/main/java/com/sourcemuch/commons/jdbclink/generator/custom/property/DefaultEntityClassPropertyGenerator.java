package com.sourcemuch.commons.jdbclink.generator.custom.property;

import org.apache.commons.lang3.StringUtils;

/**
 * 实体类属性名生成器
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public class DefaultEntityClassPropertyGenerator implements IEntityClassPropertyGenerator{

	@Override
	public String generateProperty(String columnName) {
		
		String[] strings = columnName.split("_");
		if (strings.length > 1) {
			String prop = "";
			for (String s : strings) {
				if (prop == "") {
					prop += s;
					continue;
				}
				prop += StringUtils.capitalize(s);
			}
			return prop;
		}
		return columnName;
		
	}
	
	

}
