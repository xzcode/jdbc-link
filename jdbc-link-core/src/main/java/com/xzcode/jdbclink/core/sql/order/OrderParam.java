package com.xzcode.jdbclink.core.sql.order;

public class OrderParam {
	
	private String tableAlias = "";
	
	private String orderBy;
	
	private String sortBy;
	
	private boolean isSactisfy = true;
	
	public OrderParam() {
	}
	
	

	public OrderParam(String orderBy, String sortBy) {
		super();
		this.orderBy = orderBy;
		this.sortBy = sortBy;
	}
	
	public OrderParam(String tableAlias ,String orderBy, String sortBy) {
		super();
		this.orderBy = orderBy;
		this.sortBy = sortBy;
		this.tableAlias = tableAlias;
	}
	
	
	public OrderParam(boolean isSactisfy, String tableAlias, String orderBy, String sortBy) {
		super();
		this.isSactisfy = isSactisfy;
		this.tableAlias = tableAlias;
		this.orderBy = orderBy;
		this.sortBy = sortBy;
	}



	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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
}
