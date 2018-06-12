package com.xzcode.jdbclink.core.sql.groupby;

import org.apache.commons.lang3.StringUtils;

public class GroupByParam {
	
	private String alias = "";
	
	private String column;
	
	
	
	public GroupByParam() {
		super();
	}

	public GroupByParam(String column) {
		super();
		this.column = column;
	}

	public GroupByParam(String alias, String column) {
		super();
		this.alias = alias;
		this.column = column;
	}

	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		if (StringUtils.isEmpty(alias)) {
			return;
		}
		this.alias = alias;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
	
	

}
