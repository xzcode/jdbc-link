package com.sourcemuch.commons.jdbclink.generator.custom.comment.column;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sourcemuch.commons.jdbclink.generator.custom.comment.column.ColumnCommentEnumProperty.DataTypeEnumValues;

/**
 * 列注释枚举类解析器 默认实现，解析实例：图书当前借阅情况[0 已借出 no; 1 可借出 yes]
 * 
 * 
 * @author zai
 * 2018-04-17
 */
public class DefaultColumnCommentParser implements IColumnCommentParser {

	@Override
	public ColumnComment parse(String columnComment) {
		
		if (columnComment == null || columnComment.trim().equals("")) {
			return null;
		}
		
		ColumnComment colComment = new ColumnComment();
		
		Matcher matcher1 = Pattern.compile("((\\s|\\S)*)\\[((\\s|\\S)*)?\\]").matcher(columnComment);
		if (matcher1.find()) {
			String clearComment = matcher1.group(1).trim();
			colComment.setClearComment(clearComment);
			String[] arr = matcher1.group(3).split(";");
			if (arr.length > 0) {
				List<ColumnCommentEnumProperty> props = new LinkedList<>();
				for (String str : arr) {
					String[] split = str.trim().replaceAll(" {2,}", " ").split(" ");
					String name = split[2].trim();
					String desc = split[1].trim().toUpperCase();
					String value = split[0].trim();
					ColumnCommentEnumProperty enumProperty = new ColumnCommentEnumProperty(name, desc, value);
					
					if (
							(value.startsWith("\"") && value.endsWith("\""))
							||
							(value.startsWith("'") && value.endsWith("'"))
						) {
						enumProperty.setDataType(DataTypeEnumValues.STRING);
					}
					
					props.add(enumProperty);
				}
				colComment.setEnumProperties(props);
			}
		}else {
			colComment.setClearComment(columnComment);
		}
		
		return colComment;
		
	}

}
