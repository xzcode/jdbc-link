package com.xzcode.jdbclink.core.entity;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import com.xzcode.jdbclink.core.exception.JdbcLinkRuntimeException;

public class EntityFieldInfo {
	
	//属性对象
	private Field field;
	
	private Class<?> fieldClass;
	
	//属性名称
	private String propName;
	
	//数据库字段名称
	private String column;
	
	//数据库类型  ava.sql.Types
	//private int sqlType;
	
	//get方法对象
	//private Method getMethod;
	
	//set方法对象
	//private Method setMethod;
	
	//是否可NULL
	private boolean nullable;
	
	//最大长度限制
	private int length;
	
	public void setValue(Object target, Object value) {
		if (target == null) {
			throw new JdbcLinkRuntimeException("Parameter 'target' could not be null!");
		}
		
		if (!nullable) {
			if (value == null) {
				throw new JdbcLinkRuntimeException("Parameter 'value' could not be null!");
			}
			
			if (length > -1) {
				
				if (value instanceof String && ((String) value).length() > this.length) {
					throw new JdbcLinkRuntimeException("Parameter 'value' length must be less than " + length + "!");
				}else if (StringUtils.isNumeric(value.toString())) {
					String valStr = value.toString();
					if (valStr.length() > this.length) {
						throw new JdbcLinkRuntimeException("Parameter 'value' length must be less than " + length + "!");
					}
				}
				throw new JdbcLinkRuntimeException("Parameter 'value' could not be null!");
			}
		}
		
		try {
			this.field.set(target, value);
		} catch (Exception e) {
			throw new JdbcLinkRuntimeException(e);
		}
		
	}
	
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
		this.field.setAccessible(true);
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}


	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	

	public Class<?> getFieldClass() {
		return fieldClass;
	}

	public void setFieldClass(Class<?> fieldClass) {
		this.fieldClass = fieldClass;
	}

	
}
