package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.param.Param;

public interface ParamAble<T> {
	
	void addParam(Param<T> param);
	
	T getThis();
	
	public default Param<T> and() {
		Param<T> param = new Param<T> ("and",getThis());
		this.addParam(param);
		return param;
	
	}
	
	

	public default Param<T> and(boolean isSatisfy) {
		Param<T> param = new Param<T> ("and",getThis());
		param.setIsSatisfy(isSatisfy);
		this.addParam(param);
		return param;
	}
	
	public default Param<T> and(Satisfy satisfy) {
		return and(satisfy.isSatisfy());
	}
	
	public default Param<T> or() {
		Param<T> param = new Param<T> ("or",getThis());
		this.addParam(param);
		return param;
	
	}
	public default Param<T> or(boolean isSatisfy) {
		Param<T> param = new Param<T> ("or",getThis());
		param.setIsSatisfy(isSatisfy);
		this.addParam(param);
		return param;
	
	}
	
	public default Param<T> or(Satisfy satisfy) {
		return or(satisfy.isSatisfy());
	}
	

}
