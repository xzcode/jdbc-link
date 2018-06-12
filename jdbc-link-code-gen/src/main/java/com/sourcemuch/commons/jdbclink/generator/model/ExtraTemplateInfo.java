package com.sourcemuch.commons.jdbclink.generator.model;

import com.sourcemuch.commons.jdbclink.generator.custom.extra.DefaultExtraDataGenerator;
import com.sourcemuch.commons.jdbclink.generator.custom.extra.DefaultExtraDirPartGenerator;
import com.sourcemuch.commons.jdbclink.generator.custom.extra.DefaultExtraOutputFilenameGenerator;
import com.sourcemuch.commons.jdbclink.generator.custom.extra.IExtraTemplateDataGenerator;
import com.sourcemuch.commons.jdbclink.generator.custom.extra.IExtraTemplateDirPartGenerator;
import com.sourcemuch.commons.jdbclink.generator.custom.extra.IExtraTemplateOutputFilenameGenerator;

/**
 * 额外模版信息
 * 
 * 
 * @author zai
 * 2018-04-19
 */
public class ExtraTemplateInfo {
	
	/**
	 * 
	 * 基础输出路径
	 */
	private String basicOutputPath;
	
	/**
	 * 额外模版
	 */
	private String template;
	
	/**
	 * 自定义额外路径生成器
	 */
	private IExtraTemplateDirPartGenerator dirPartGenerator = new DefaultExtraDirPartGenerator();
	
	/**
	 * 输出名称生成器
	 */
	private IExtraTemplateOutputFilenameGenerator outputFilenameGenerator = new DefaultExtraOutputFilenameGenerator();
	
	
	/**
	 * 模版数据生成器
	 */
	private IExtraTemplateDataGenerator dataGenerator = new DefaultExtraDataGenerator();
	
	
	public ExtraTemplateInfo() {
	}
	
	


	public ExtraTemplateInfo(String basicOutputPath, String template,
			IExtraTemplateOutputFilenameGenerator outputFilenameGenerator) {
		super();
		this.basicOutputPath = basicOutputPath;
		this.template = template;
		this.outputFilenameGenerator = outputFilenameGenerator;
	}




	public ExtraTemplateInfo(String basicOutputPath, String template, IExtraTemplateDirPartGenerator dirPartGenerator,
			IExtraTemplateOutputFilenameGenerator outputFilenameGenerator) {
		super();
		this.basicOutputPath = basicOutputPath;
		this.template = template;
		this.dirPartGenerator = dirPartGenerator;
		this.outputFilenameGenerator = outputFilenameGenerator;
	}


	public static ExtraTemplateInfo create() {
		return new ExtraTemplateInfo();
	}


	public String getTemplate() {
		return template;
	}

	public ExtraTemplateInfo setTemplate(String template) {
		this.template = template;
		return this;
	}

	public IExtraTemplateOutputFilenameGenerator getOutputFilenameGenerator() {
		return outputFilenameGenerator;
	}

	public ExtraTemplateInfo setOutputFilenameGenerator(IExtraTemplateOutputFilenameGenerator outputFilenameGenerator) {
		this.outputFilenameGenerator = outputFilenameGenerator;
		return this;
	}
	
	public IExtraTemplateDirPartGenerator getDirPartGenerator() {
		return dirPartGenerator;
	}
	
	public ExtraTemplateInfo setDirPartGenerator(IExtraTemplateDirPartGenerator dirPartGenerator) {
		this.dirPartGenerator = dirPartGenerator;
		return this;
	}
	
	
	public String getBasicOutputPath() {
		return basicOutputPath;
	}
	
	public ExtraTemplateInfo setBasicOutputPath(String basicOutputPath) {
		this.basicOutputPath = basicOutputPath;
		return this;
	}
	
	public ExtraTemplateInfo setDataGenerator(IExtraTemplateDataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
		return this;
	}
	
	public IExtraTemplateDataGenerator getDataGenerator() {
		return dataGenerator;
	}
	
}
