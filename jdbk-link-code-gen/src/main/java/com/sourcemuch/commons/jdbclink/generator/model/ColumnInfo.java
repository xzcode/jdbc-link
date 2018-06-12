package com.sourcemuch.commons.jdbclink.generator.model;

import com.sourcemuch.commons.jdbclink.generator.custom.comment.column.ColumnComment;

/**
 * 列信息
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public class ColumnInfo {
	
	/**
	 * 列名
	 */
	private String name;
	
	/**
	 * 属性名
	 */
	private String propertyName;
	
	/**
	 * 数据类型
	 */
	private String type;
	
	/**
	 * java数据类型
	 */
	private String javaType;
	
	/**
	 * java 类型全包名，如：java.util.List;
	 */
	private String fullPackageJavaType;
	
	/**
	 * 数据长度
	 */
	private Integer length;
	
	/**
	 * 小数点长度
	 */
	private Integer pointLength;
	
	/**
	 * 是否可为null
	 */
	private boolean nullable;
	
	/**
	 * 是否主键
	 */
	private boolean isPrimaryKey;
	
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	/**
	 * 注释
	 */
	private String comment;
	
	/**
	 * 注释转换对象
	 */
	private ColumnComment columnComment;
	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Integer getLength() {
		return length;
	}
	
	public void setLength(Integer length) {
		this.length = length;
	}
	
	
	public Integer getPointLength() {
		return pointLength;
	}
	
	public void setPointLength(Integer pointLength) {
		this.pointLength = pointLength;
	}
	

	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String getJavaType() {
		return javaType;
	}
	
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	public String getFullPackageJavaType() {
		return fullPackageJavaType;
	}
	
	public void setFullPackageJavaType(String fullPackageJavaType) {
		this.fullPackageJavaType = fullPackageJavaType;
	}
	
	public ColumnComment getColumnComment() {
		return columnComment;
	}
	
	public void setColumnComment(ColumnComment columnComment) {
		this.columnComment = columnComment;
	}
}
