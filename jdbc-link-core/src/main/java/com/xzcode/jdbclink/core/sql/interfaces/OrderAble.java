package com.xzcode.jdbclink.core.sql.interfaces;

import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.order.OrderParam;

public interface OrderAble<T> extends GetSelectAble<T>{
	
	
	
	public default OrderParam<T> orderBy(EntityField orderBy) {
		return this.orderBy(true, orderBy.getTableAlias(), orderBy);
	}
	
	public default Select<T> orderByAsc(String tableAlias, EntityField orderBy) {
		return this.orderBy(true, tableAlias, orderBy).asc();
	}
	public default Select<T> orderByAsc(EntityField orderBy) {
		return this.orderBy(true, orderBy.getTableAlias(), orderBy).asc();
	}
	
	public default Select<T> orderByDesc(EntityField orderBy) {
		return this.orderBy(true, orderBy.getTableAlias(), orderBy).desc();
	}
	
	public default Select<T> orderByDesc(String tableAlias, EntityField orderBy) {
		return this.orderBy(true, tableAlias, orderBy).desc();
	}
	
	public default OrderParam<T> orderBy(String tableAlias, EntityField orderBy){
		return this.orderBy(true, tableAlias, orderBy);
	}
	
	
	public default OrderParam<T> orderBy(boolean isSactisfy, EntityField orderBy) {
		return this.orderBy(isSactisfy, orderBy.getTableAlias() ,orderBy);
	}
	
	
	
	
	public default OrderParam<T> orderBy(boolean isSactisfy, String tableAlias, EntityField orderBy) {
		return this.getSelect().orderBy(isSactisfy, tableAlias, orderBy);
	}
	
	public default Select<T> orderBy(String orderBy, String sorting){
		return this.orderBy(true, null, orderBy, sorting);
	}
	
	public default Select<T> orderBySorting(String orderBy, String sorting){
		return this.orderBy(true, null, orderBy, sorting);
	}
	
	public default Select<T> orderBy(String tableAlias, String orderBy, String sorting){
		return this.orderBy(true, tableAlias, orderBy, sorting);
	}
	
	public default Select<T> orderBy(boolean isSactisfy, String tableAlias, String orderBy, String sorting){
		return this.getSelect().orderBy(tableAlias, orderBy, sorting);
	}

	

	

	


	
}
