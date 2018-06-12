package com.xzcode.jdbclink.core.sql.groupby.having;

import java.util.LinkedList;
import java.util.List;

import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.interfaces.ExecuteAble;
import com.xzcode.jdbclink.core.sql.interfaces.GroupAble;
import com.xzcode.jdbclink.core.sql.interfaces.LimitAble;
import com.xzcode.jdbclink.core.sql.interfaces.OrderAble;
import com.xzcode.jdbclink.core.sql.interfaces.ParamAble;
import com.xzcode.jdbclink.core.sql.interfaces.QueryAble;
import com.xzcode.jdbclink.core.sql.limit.LimitParam;
import com.xzcode.jdbclink.core.sql.param.Param;
import com.xzcode.jdbclink.core.sql.param.ParamGroup;

public class Having<T, C> implements ExecuteAble, LimitAble<Having<T, C>>, OrderAble<C>, QueryAble<C>,ParamAble<Having<T, C>>, GroupAble<Having<T, C>>{
	
	protected T t;
	
	protected List<Param<Having<T, C>>> params;
	
	protected List<ParamGroup<Having<T, C>>> paramGroups;
	
	public Having(T t) {
		this.t = t;
	}

	
	
	@Override
	public void addParam(Param<Having<T, C>> param){
		if (this.params == null) {
			this.params = new LinkedList<>();
		}
		this.params.add(param);
	}
	
	@Override
	public void addParamGroup(ParamGroup<Having<T, C>> paramGroup){
		if (this.paramGroups == null) {
			this.paramGroups = new LinkedList<>();
		}
		this.paramGroups.add(paramGroup);
	}
	
	@Override
	public Having<T, C> getThis() {
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Select<C> getSelect() {
		return (Select<C>) t;
	}
	//---------------------------------
	
	public List<Param<Having<T, C>>> getParams() {
		return this.params;
	}
	
	public List<ParamGroup<Having<T, C>>> getParamGroups() {
		return paramGroups;
	}

	
	@Override
	public ExecuteAble getExecuteAble() {
		return (ExecuteAble) t;
	}


	@Override
	public Having<T, C> setLimitParam(LimitParam limitParam) {
		
		this.getSelect().setLimit(limitParam);
		
		return this;
	}

}
