package com.xzcode.jdbclink.core.sql.order;

import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.sql.Select;

public class OrderParam<T> {
	
	private String tableAlias = "";
	
	private String orderBy;
	
	private String sortBy;
	
	private boolean isSactisfy = true;
	
	private Select<T> select;
	
	public OrderParam() {
	}
	
	public OrderParam(EntityField orderBy, Select<T> select) {
		this.select = select;
		this.orderBy = orderBy.fieldName();
		this.tableAlias = orderBy.tableAlias();
	}
	public OrderParam(String tableAlias, EntityField orderBy, Select<T> select) {
		this.select = select;
		this.orderBy = orderBy.fieldName();
		this.tableAlias = tableAlias;
	}
	
	
	public OrderParam(boolean isSactisfy, EntityField orderBy, Select<T> select) {
		this.select = select;
		this.orderBy = orderBy.fieldName();
		this.tableAlias = orderBy.tableAlias();
		this.isSactisfy = isSactisfy;
	}
	
	public OrderParam(boolean isSactisfy, String tableAlias, EntityField orderBy, Select<T> select) {
		this.select = select;
		this.orderBy = orderBy.fieldName();
		this.tableAlias = tableAlias;
		this.isSactisfy = isSactisfy;
	}

	
	public OrderParam(String tableAlias ,String orderBy, String sortBy, Select<T> select) {
		super();
		this.orderBy = orderBy;
		this.sortBy = sortBy;
		setTableAlias(tableAlias);
	}
	
	
	public OrderParam(boolean isSactisfy, String tableAlias, String orderBy, String sortBy, Select<T> select) {
		super();
		this.isSactisfy = isSactisfy;
		setTableAlias(tableAlias);
		this.orderBy = orderBy;
		this.sortBy = sortBy;
	}
	
	public Select<T> asc() {
		this.sortBy = "asc";
		return this.select;
	}



	public Select<T> desc() {
		this.sortBy = "desc";
		return this.select;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public String getTableAlias() {
		return tableAlias;
	}
	
	public void setTableAlias(String tableAlias) {
		if (tableAlias == null) {
			return;
		}
		this.tableAlias = tableAlias;
	}
	
	public boolean isSactisfy() {
		return isSactisfy;
	}
	
	public void setSactisfy(boolean isSactisfy) {
		this.isSactisfy = isSactisfy;
	}
	
	public Select<?> getSelect() {
		return select;
	}
}
