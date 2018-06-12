package com.xzcode.jdbclink.codegen.custom.extra;

import com.xzcode.jdbclink.codegen.model.TableEntityInfo;

/**
 * 自定义模版输出文件额外路径生成器
 * 
 * 
 * @author zai
 * 2018-04-19
 */
public interface IExtraTemplateDirPartGenerator {
	
	
	public String generate(String template, TableEntityInfo tableEntityInfo);
}
