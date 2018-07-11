package com.xzcode.jdbclink.core.sql.param;

import java.util.LinkedList;
import java.util.List;

import com.xzcode.jdbclink.core.sql.interfaces.GroupAble;
import com.xzcode.jdbclink.core.sql.interfaces.ParamAble;

/**
 * 参数分组
 * @param <T>
 * 
 * @author zai 2017-06-08 21:35:23
 */
public class ParamGroup<T> implements ParamAble<ParamGroup<T>>, GroupAble<ParamGroup<T>>{
	
	protected boolean isSatisfy = true;
	
	protected T t;

	protected String connect;// 'and' or 'or'
	
	
	protected List<Param<ParamGroup<T>>> params;
	
	protected List<ParamGroup<ParamGroup<T>>> paramGroups;
	
	public ParamGroup(T t) {
		this.t = t;
	}
	
	public ParamGroup(T t, String connect) {
		this.t = t;
		this.connect = connect;
	}
	
	/*public Param<ParamGroup<T>> and() {
		 return  this.addParam(new Param<ParamGroup<T>>("and", this));
	}
	
	public Param<ParamGroup<T>> and(boolean isSatisfy) {
		 return  this.addParam(new Param<ParamGroup<T>>("and", isSatisfy, this));
	}
	
	public Param<ParamGroup<T>> and(Satisfy satisfy) {
		 return  this.addParam(new Param<ParamGroup<T>>("and", satisfy.isSatisfy(), this));
	}
	
	public Param<ParamGroup<T>> or() {
		 return  this.addParam(new Param<ParamGroup<T>>("or", this));
	}
	
	public Param<ParamGroup<T>> or(boolean isSatisfy) {
		 return  this.addParam(new Param<ParamGroup<T>>("or", isSatisfy, this));
	}
	
	public Param<ParamGroup<T>> or(Satisfy satisfy) {
		 return  this.addParam(new Param<ParamGroup<T>>("or", satisfy.isSatisfy(), this));
	}*/
	
	
	public void addParam(Param<ParamGroup<T>> param) {
		if (this.params == null) {
			this.params = new LinkedList<>();
		}
		this.params.add(param);
	}
	
	public T endGroup() {
		return this.t;
	}
	
	
	//------------------------------
	
	public String getConnect() {
		return connect;
	}
	
	public List<ParamGroup<ParamGroup<T>>> getParamGroups() {
		return paramGroups;
	}
	
	public List<Param<ParamGroup<T>>> getParams() {
		return params;
	}
	
	public void setIsSatisfy(boolean isSatisfy) {
		this.isSatisfy = isSatisfy;
	}
	
	public boolean getIsSatisfy() {
		return isSatisfy;
	}


	@Override
	public ParamGroup<T> getThis() {
		return this;
	}

	@Override
	public void addParamGroup(ParamGroup<ParamGroup<T>> paramGroup) {
		if (this.paramGroups == null) {
			this.paramGroups = new LinkedList<>();
		}
		this.paramGroups.add(paramGroup);
		
	}

}
