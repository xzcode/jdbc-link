package com.xzcode.jdbclink.codegen.custom.extra;

import com.xzcode.jdbclink.codegen.model.TableEntityInfo;

/**
 * 额外模版生成器输出文件名生成器
 * 
 * 
 * @author zai
 * 2018-04-19
 */
public interface IExtraTemplateOutputFilenameGenerator {
	
	
	public String generate(String template, TableEntityInfo tableEntityInfo);
}
