package com.xzcode.jdbclink.codegen.custom.extra;

import java.util.Map;

import com.xzcode.jdbclink.codegen.model.TableEntityInfo;

/**
 * 额外模版数据生成器
 * 
 * 
 * @author zai
 * 2018-04-19
 */
public interface IExtraTemplateDataGenerator {
	
	
	public Map<String, Object> generate(TableEntityInfo tableEntityInfo);
}
