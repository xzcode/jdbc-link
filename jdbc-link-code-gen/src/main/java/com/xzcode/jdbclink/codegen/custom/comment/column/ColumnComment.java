package com.xzcode.jdbclink.codegen.custom.comment.column;

import java.util.List;

public class ColumnComment {
	
	/**
	 * 干净注释
	 */
	private String clearComment;
	
	/**
	 * 列枚举属性
	 */
	private List<ColumnCommentEnumProperty> enumProperties;

	public String getClearComment() {
		return clearComment;
	}

	public void setClearComment(String clearComment) {
		this.clearComment = clearComment;
	}

	public List<ColumnCommentEnumProperty> getEnumProperties() {
		return enumProperties;
	}

	public void setEnumProperties(List<ColumnCommentEnumProperty> enumProperties) {
		this.enumProperties = enumProperties;
	}

	
	

}
