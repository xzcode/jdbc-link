package com.xzcode.jdbclink.core.sql.groupby;

import org.apache.commons.lang3.StringUtils;

import com.xzcode.jdbclink.core.entity.model.EntityField;

public class GroupByParam {
	
	private String alias = "";
	
	private EntityField column;
	
	
	
	public GroupByParam() {
		super();
	}

	public GroupByParam(EntityField column) {
		super();
		this.column = column;
	}

	public GroupByParam(String alias, EntityField column) {
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

	public EntityField getColumn() {
		return column;
	}

	public void setColumn(EntityField column) {
		this.column = column;
	}
	
	

}
