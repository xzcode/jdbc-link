package com.xzcode.jdbclink.codegen.custom.comment.column;

/**
 * 列注释枚举类解析器
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public interface IColumnCommentParser {
	
	/**
	 * 执行解析转换
	 * @param columnComment
	 * @return
	 * 
	 * @author zai
	 * 2018-04-17
	 */
	public ColumnComment parse(String columnComment);

}
