package com.xzcode.jdbclink.codegen.config;


/**
 * 配置类
 */
public class JdbcLinkEntityGeneratorConfig{
	
	/** 数据库url. */
	private String dbUrl;
	
	/** 数据库用户名. */
	private String dbUserName;
	
	/** 数据库密码. */
	private String dbPassword;
	
	/** 实体类所在数据库名称. */
	private String entityDatabaseName;
	
	/** 项目基础包. */
	private String entityBasicPackage;
	
	/** 实体类基础输出路径. */
	private String entityBasicOutputPath;
	

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

	public String getEntityBasicPackage() {
		return entityBasicPackage;
	}

	public void setEntityBasicPackage(String entityBasicPackage) {
		this.entityBasicPackage = entityBasicPackage;
	}
	
	public String getEntityBasicOutputPath() {
		return entityBasicOutputPath;
	}
	
	public void setEntityBasicOutputPath(String entityBasicOutputPath) {
		this.entityBasicOutputPath = entityBasicOutputPath;
	}
	
	
	public String getEntityDatabaseName() {
		return entityDatabaseName;
	}
	
	public void setEntityDatabaseName(String entityDatabaseName) {
		this.entityDatabaseName = entityDatabaseName;
	}


}
