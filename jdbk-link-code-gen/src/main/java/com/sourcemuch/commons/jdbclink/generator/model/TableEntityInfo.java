package com.sourcemuch.commons.jdbclink.generator.model;

import java.util.List;
import java.util.Set;

/**
 * 表信息.
 *
 * @author zai
 * 2018-04-14
 */
public class TableEntityInfo {
	/** 数据库名称. */
	private String databaseName;
	
	/** 表名. */
	private String tableName;
	
	/** 实体类名称. */
	private String entityClassName;
	
	/** 列信息. */
	private List<ColumnInfo> columns;
	
	/** 表注释. */
	private String tableComment;
	
	/** 实体类全包名. */
	private String fullPackageName;
	
	/** 带全包名的实体类名称. */
	private String fullPackageClassName;
	
	/** 需要带入的java类型 的全包名 */
	private Set<String> importJavaTypes;
	
	/**模块名称*/
	private String moduleName;
	
	
	public Set<String> getImportJavaTypes() {
		return importJavaTypes;
	}
	
	public void setImportJavaTypes(Set<String> importJavaTypes) {
		this.importJavaTypes = importJavaTypes;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
	public String getEntityClassName() {
		return entityClassName;
	}
	
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}
	
	
	public String getDatabaseName() {
		return databaseName;
	}
	
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public String getFullPackageName() {
		return fullPackageName;
	}
	
	public void setFullPackageName(String fullPackageName) {
		this.fullPackageName = fullPackageName;
	}
	
	public void setFullPackageClassName(String fullPackageClassName) {
		this.fullPackageClassName = fullPackageClassName;
	}
	public String getFullPackageClassName() {
		return fullPackageClassName;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
