package com.xzcode.jdbclink.core;

import java.util.List;

public class EntityInfo {
	
	private String id;
	private Class<?> idClass;
	private String table;
	private String prefix;
	private String alias;
	private Class<?> clazz;
	private List<String> props;
	private List<String> columns;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Class<?> getIdClass() {
		return idClass;
	}
	
	public void setIdClass(Class<?> idClass) {
		this.idClass = idClass;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<String> getProps() {
		return props;
	}

	public void setProps(List<String> props) {
		this.props = props;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	

}
