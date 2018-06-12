package com.sourcemuch.commons.jdbclink.generator.config;

import java.util.List;
import java.util.Map;

import com.sourcemuch.commons.jdbclink.generator.model.ExtraTemplateInfo;

/**
 * 额外模版生成配置类
 */
public class JdbcLinkExtraGeneratorConfig{
	
	/** 数据库url. */
	private String dbUrl;
	
	/** 数据库用户名. */
	private String dbUserName;
	
	/** 数据库密码. */
	private String dbPassword;
	
	/** 数据库名称. */
	private String databaseName;
	
	/** 模版文件路径. */
	private String templatePath;
	
	/**与模版文件在同一资源文件夹的java class对象*/
	private Class<?> templateLoaderClass;
	
	/**
	 * 模版信息
	 */
	List<ExtraTemplateInfo> templateInfos;
	
	/**
	 * 附加参数
	 */
	Map<String, Object> extraData;
	
	
	public JdbcLinkExtraGeneratorConfig() {
	}
	
	

	public JdbcLinkExtraGeneratorConfig(
			String dbUrl, 
			String dbUserName, 
			String dbPassword, 
			String databaseName,
			String templatePath, 
			Class<?> templateLoaderClass,
			List<ExtraTemplateInfo> templateInfos, 
			Map<String, Object> extraData
			) {
		super();
		this.dbUrl = dbUrl;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
		this.databaseName = databaseName;
		this.templatePath = templatePath;
		this.templateLoaderClass = templateLoaderClass;
		this.templateInfos = templateInfos;
		this.extraData = extraData;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Class<?> getTemplateLoaderClass() {
		return templateLoaderClass;
	}

	public void setTemplateLoaderClass(Class<?> templateLoaderClass) {
		this.templateLoaderClass = templateLoaderClass;
	}

	public List<ExtraTemplateInfo> getTemplateInfos() {
		return templateInfos;
	}
	
	public void setTemplateInfos(List<ExtraTemplateInfo> templateInfos) {
		this.templateInfos = templateInfos;
	}

	public void setExtraData(Map<String, Object> extraData) {
		this.extraData = extraData;
	}
	
	public Map<String, Object> getExtraData() {
		return extraData;
	}
}
