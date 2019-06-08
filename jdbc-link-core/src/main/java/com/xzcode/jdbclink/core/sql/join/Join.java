package com.xzcode.jdbclink.core.sql.join;

import java.util.LinkedList;
import java.util.List;

import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.groupby.GroupByParam;
import com.xzcode.jdbclink.core.sql.interfaces.AliasAndPrefix;
import com.xzcode.jdbclink.core.sql.interfaces.GroupAble;
import com.xzcode.jdbclink.core.sql.interfaces.GroupByAble;
import com.xzcode.jdbclink.core.sql.interfaces.JoinAble;
import com.xzcode.jdbclink.core.sql.interfaces.OrderAble;
import com.xzcode.jdbclink.core.sql.interfaces.ParamAble;
import com.xzcode.jdbclink.core.sql.interfaces.QueryAble;
import com.xzcode.jdbclink.core.sql.interfaces.Satisfy;
import com.xzcode.jdbclink.core.sql.interfaces.WhereAble;
import com.xzcode.jdbclink.core.sql.param.Param;
import com.xzcode.jdbclink.core.sql.param.ParamGroup;


public class Join<T, C> implements QueryAble<C>,GroupByAble<Select<C>>,OrderAble<C>,WhereAble<T, C>,JoinAble<T, C>,AliasAndPrefix, ParamAble<Join<T, C>>, GroupAble<Join<T, C>>{
	
	protected JdbcLinkConfig config;
	
	protected List<Param<Join<T, C>>> params;
	
	protected List<ParamGroup<Join<T, C>>> paramGroups;
	
	protected String joinTag = "inner join";
	
	protected String alias = "";
	
	protected String prefix = "";
	
	protected EntityInfo entityInfo;
	
	protected JoinAble<T, C> joinAble;
	
	
	public Join(JdbcLinkConfig config, EntityInfo entityInfo, JoinAble<T, C> joinAble) {
		this.config = config;
		this.joinAble = joinAble;
		this.entityInfo = entityInfo;
	}
	
	public Join(JdbcLinkConfig config, EntityInfo entityInfo, JoinAble<T, C> joinAble, String alias) {
		this.config = config;
		this.joinAble = joinAble;
		this.entityInfo = entityInfo;
	}
	
	public Join(JdbcLinkConfig config, EntityInfo entityInfo, JoinAble<T, C> joinAble, String alias, String prefix) {
		this.config = config;
		this.joinAble = joinAble;
		this.alias = alias;
		this.prefix = prefix;
		this.entityInfo = entityInfo;
	}
	
	public Join(JdbcLinkConfig config, EntityInfo entityInfo, JoinAble<T, C> joinAble, String alias, String prefix, String joinTag) {
		this.config = config;
		this.joinAble = joinAble;
		this.alias = alias;
		this.joinTag = joinTag;
		this.entityInfo = entityInfo;
	}

	
	public Param<Join<T, C>> param() {
		Param<Join<T, C>> param = new Param<>(this);
		params.add(param);
		return param;
	}
	
	public Param<Join<T, C>> on() {
		Param<Join<T, C>> param = new Param<>(this);
		this.addParam(param);
		return param;
	}
	
	public Param<Join<T, C>> on(boolean isSatisfy) {
		Param<Join<T, C>> param = new Param<>(this);
		param.setIsSatisfy(isSatisfy);
		this.addParam(param);
		return param;
	}
	
	public Param<Join<T, C>> on(Satisfy satisfy) {
		return on(satisfy.isSatisfy());
	}
	
	

	//----------------------------
	public String getJoinTag() {
		return joinTag;
	}

	public void setJoinTag(String joinTag) {
		this.joinTag = joinTag;
	}


	public EntityInfo getEntityInfo() {
		return entityInfo;
	}

	public void setEntityInfo(EntityInfo entityInfo) {
		this.entityInfo = entityInfo;
	}

	
	
	public String getMainAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getPrefix() {
		return prefix;
	}

	@Override
	public void addParamGroup(ParamGroup<Join<T, C>> paramGroup) {
		if (this.paramGroups == null) {
			this.paramGroups = new LinkedList<>();
		}
		this.paramGroups.add(paramGroup);
	}

	@Override
	public void addParam(Param<Join<T, C>> param) {
		if (this.params == null) {
			this.params = new LinkedList<>();
		}
		this.params.add(param);
	}

	@Override
	public Join<T, C> getThis() {
		return this;
	}

	@Override
	public void addJoins(String alias, Join<T, C> join) {
		this.joinAble.addJoins(alias, join);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public WhereAble<T, C> getWhereAble() {
		return (WhereAble<T, C>) joinAble;
	}
	
	public List<Param<Join<T, C>>> getParams() {
		return params;
	}
	
	public List<ParamGroup<Join<T, C>>> getParamGroups() {
		return paramGroups;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Select<C> getSelect() {
		if (this.joinAble instanceof Select) {
			return (Select<C>) joinAble;			
		}else if (this.joinAble instanceof Join){
			return getSelectFromJoin((Join<T, C>) joinAble);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Select<C> getSelectFromJoin(Join<T, C> joinAble) {
		JoinAble<T, C> joinAble2 = joinAble;
		do {
			joinAble2 = ((Join<T, C>)joinAble2).getJoinAble();
		} while (!(joinAble2 instanceof Select));
		return (Select<C>) joinAble2;
	}

	
	@Override
	public Select<C> addGroupByParams(GroupByParam groupByParam) {
		
		return this.getSelect().addGroupByParams(groupByParam);
	}
	
	public JoinAble<T, C> getJoinAble() {
		return joinAble;
	}

	@Override
	public JdbcLinkConfig getConfig() {
		return config;
	}
	
	public void setConfig(JdbcLinkConfig config) {
		this.config = config;
	}
}
