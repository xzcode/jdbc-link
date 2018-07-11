package com.xzcode.jdbclink.core.sql.where;

import java.util.LinkedList;
import java.util.List;

import com.xzcode.jdbclink.core.sql.Delete;
import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.groupby.GroupByParam;
import com.xzcode.jdbclink.core.sql.interfaces.ExecuteAble;
import com.xzcode.jdbclink.core.sql.interfaces.GroupAble;
import com.xzcode.jdbclink.core.sql.interfaces.GroupByAble;
import com.xzcode.jdbclink.core.sql.interfaces.LimitAble;
import com.xzcode.jdbclink.core.sql.interfaces.OrderAble;
import com.xzcode.jdbclink.core.sql.interfaces.ParamAble;
import com.xzcode.jdbclink.core.sql.interfaces.QueryAble;
import com.xzcode.jdbclink.core.sql.limit.LimitParam;
import com.xzcode.jdbclink.core.sql.param.Param;
import com.xzcode.jdbclink.core.sql.param.ParamGroup;
import com.xzcode.jdbclink.core.sql.update.UpdateSet;

public class Where<T, C> implements GroupByAble<Select<C>>,ExecuteAble, LimitAble<Where<T, C>>, OrderAble<C>, QueryAble<C>,ParamAble<Where<T, C>>, GroupAble<Where<T, C>>{
	
	protected T t;
	
	protected List<Param<Where<T, C>>> params;
	
	protected List<ParamGroup<Where<T, C>>> paramGroups;
	
	protected LimitParam limit;
	
	public Where(T t) {
		this.t = t;
	}

	
	
	@Override
	public void addParam(Param<Where<T, C>> param){
		if (this.params == null) {
			this.params = new LinkedList<>();
		}
		this.params.add(param);
	}
	
	@Override
	public void addParamGroup(ParamGroup<Where<T, C>> paramGroup){
		if (this.paramGroups == null) {
			this.paramGroups = new LinkedList<>();
		}
		this.paramGroups.add(paramGroup);
	}
	
	@Override
	public Where<T, C> getThis() {
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Select<C> getSelect() {
		return (Select<C>) t;
	}
	//---------------------------------
	
	public List<Param<Where<T, C>>> getParams() {
		return this.params;
	}
	
	public List<ParamGroup<Where<T, C>>> getParamGroups() {
		return paramGroups;
	}

	
	@Override
	public ExecuteAble getExecuteAble() {
		return (ExecuteAble) t;
	}



	@Override
	public Select<C> addGroupByParams(GroupByParam groupByParam) {
		return this.getSelect().addGroupByParams(groupByParam);
	}

	public LimitParam getLimit() {
		return limit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Where<T, C> setLimitParam(LimitParam limitParam) {
		if (this.t instanceof Select) {
			((Select<C>)this.t).setLimit(limitParam);
		}else if (this.t instanceof UpdateSet) {
			((UpdateSet)this.t).setLimit(limitParam);
		}else if (this.t instanceof Delete) {
			((Delete)this.t).setLimit(limitParam);
		}
		return this;
	}


}
