package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.param.ParamGroup;

public interface GroupAble<T> {
	
	void addParamGroup(ParamGroup<T> paramGroup);
	
	T getThis();
	
	public default ParamGroup<T> paramGroup() {
		return andGroup();
	}
	
	public default ParamGroup<T> paramGroup(boolean isSatisfy) {
		return andGroup(isSatisfy);
	}
	
	public default ParamGroup<T> paramGroup(Satisfy satisfy) {
		return andGroup(satisfy);
	}
	
	public default ParamGroup<T> andGroup() {
		ParamGroup<T> paramGroup = new ParamGroup<>(getThis(), "and");
		this.addParamGroup(paramGroup);
		return paramGroup;
	}
	
	public default ParamGroup<T> andGroup(boolean isSatisfy) {
		ParamGroup<T> paramGroup = new ParamGroup<>(getThis(), "and");
		paramGroup.setIsSatisfy(isSatisfy);
		this.addParamGroup(paramGroup);
		return paramGroup;
	}
	
	public default ParamGroup<T> andGroup(Satisfy satisfy) {
		return andGroup(satisfy.isSatisfy());
	}
	
	public default ParamGroup<T> orGroup() {
		ParamGroup<T> paramGroup = new ParamGroup<>(getThis(), "or");
		this.addParamGroup(paramGroup);
		return paramGroup;
	}
	
	public default ParamGroup<T> orGroup(boolean isSatisfy) {
		ParamGroup<T> paramGroup = new ParamGroup<>(getThis(), "or");
		paramGroup.setIsSatisfy(isSatisfy);
		this.addParamGroup(paramGroup);
		return paramGroup;
	}
	
	public default ParamGroup<T> orGroup(Satisfy satisfy) {
		return orGroup(satisfy.isSatisfy());
	}

}
