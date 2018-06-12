package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.Select;

public interface OrderAble<T> extends GetSelectAble<T>{

	/*public Select<T> orderBy(String orderBy);
	
	public Select<T> orderByAsc(String orderBy);
	
	public Select<T> orderByDesc(String orderBy);

	Select<T> orderByDesc(String tableAlias, String orderBy);

	Select<T> orderByAsc(String tableAlias, String orderBy);

	Select<T> orderBy(String tableAlias, String orderBy);*/
	
	public default Select<T> orderBy(String orderBy) {
		return this.getSelect().orderBy(orderBy);
	}
	
	public default Select<T> orderBySorting(String orderBy, String sorting){
		return this.getSelect().orderBySorting(orderBy, sorting);
	}

	public default Select<T> orderByAsc(String orderBy) {
		return this.getSelect().orderByAsc(orderBy);
	}
	
	public default Select<T> orderByDesc(String orderBy) {
		return this.getSelect().orderByDesc(orderBy);
	}

	public default Select<T> orderByDesc(String tableAlias, String orderBy) {
		return this.getSelect().orderByDesc(tableAlias, orderBy);
	}

	public default Select<T> orderByAsc(String tableAlias, String orderBy) {
		return this.getSelect().orderByAsc(tableAlias, orderBy);
	}

	public default Select<T> orderBy(String tableAlias, String orderBy) {
		return this.getSelect().orderBy(tableAlias, orderBy);
	}
	
	public default Select<T> orderBy(boolean condition,String tableAlias, String orderBy) {
		return this.getSelect().orderByAsc(condition, tableAlias, orderBy);
	}
	
	public default Select<T> orderBy(boolean condition,String orderBy) {
		return this.getSelect().orderByAsc(condition, orderBy);
	}

	public default Select<T> orderByDesc(boolean condition, String tableAlias, String orderBy){
		return this.getSelect().orderByDesc(condition, tableAlias, orderBy);
	}
	
	public default Select<T> orderByDesc(boolean condition, String orderBy){
		return this.getSelect().orderByDesc(condition, orderBy);
	}
	
	public default Select<T> orderByAsc(boolean condition, String orderBy){
		return this.getSelect().orderByAsc(condition, orderBy);
	}
	
	public default Select<T> orderByAsc(boolean condition, String tableAlias, String orderBy) {
		return this.getSelect().orderByAsc(condition, tableAlias, orderBy);
	}


	


	
}
