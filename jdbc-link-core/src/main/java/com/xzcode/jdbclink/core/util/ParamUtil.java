package com.xzcode.jdbclink.core.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.xzcode.jdbclink.core.entity.model.EntityField;

public class ParamUtil {
	
	public static String getSqlParam(Object...sqlPart) {
		return Arrays.asList(sqlPart).stream()
		.map(obj -> {
			if (obj instanceof EntityField) {
				return ((EntityField)obj).getTableAlias() +"."+((EntityField)obj).getFieldName();
			}
			return String.valueOf(obj);
		})
		.collect(Collectors.joining(""));
	}

}
