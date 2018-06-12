package com.sourcemuch.commons.jdbclink.generator.custom.extra;

import com.sourcemuch.commons.jdbclink.generator.model.TableEntityInfo;

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
