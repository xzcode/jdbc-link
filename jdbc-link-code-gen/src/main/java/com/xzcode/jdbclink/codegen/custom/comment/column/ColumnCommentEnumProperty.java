package com.xzcode.jdbclink.codegen.custom.comment.column;

/**
 * 列属性枚举值 包装类
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public class ColumnCommentEnumProperty {
	
	/**
	 * 属性名
	 */
	private String name;
	
	/**
	 * 属性名说明
	 */
	private String desc;
	
	/**
	 * 属性值
	 */
	private String value;
	
	/**
	 * 属性值数据类型，int 或  String
	 */
	private String dataType = DataTypeEnumValues.INT;
	
	public static interface DataTypeEnumValues {
		String STRING = "String";
		String INT = "int";
	}
	
	public ColumnCommentEnumProperty() {
	}

	

	public ColumnCommentEnumProperty(String name, String desc, String value) {
		super();
		this.name = name;
		this.desc = desc;
		this.value = value;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	

}
