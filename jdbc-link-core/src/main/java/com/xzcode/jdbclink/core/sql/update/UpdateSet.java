package com.xzcode.jdbclink.core.sql.update;

import java.util.LinkedList;
import java.util.List;

import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.sql.interfaces.ExecuteAble;
import com.xzcode.jdbclink.core.sql.interfaces.Satisfy;
import com.xzcode.jdbclink.core.sql.interfaces.WhereAble;
import com.xzcode.jdbclink.core.sql.limit.LimitParam;
import com.xzcode.jdbclink.core.sql.param.UpdateParam;
import com.xzcode.jdbclink.core.sql.where.Where;

public class UpdateSet implements ExecuteAble,WhereAble<UpdateSet, UpdateSet>{
	
	protected Update update;

	protected List<UpdateParam> params;

	protected Where<UpdateSet, UpdateSet> where;
	
	
	protected LimitParam limit;
	
	public UpdateSet() {
	}
	
	public UpdateSet(Update update) {
		this.update = update;
	}
	
	public Where<UpdateSet, UpdateSet> where() {
		this.where = new Where<>(this);
		return this.where;
	}
	
	public UpdateParam when(Satisfy satisfy) {
		return this.when(satisfy.isSatisfy());
	}
	
	public UpdateParam when(boolean isSatisfy) {
		UpdateParam updateParam = new UpdateParam(this, isSatisfy);
		this.addParam(updateParam);
		return updateParam;
	}
	
	public UpdateSet param(EntityField field, Object value) {
		this.addParam(new UpdateParam(this, field, value));
		return this;
	}
	
	public UpdateSet param(EntityField field, EntityField field2) {
		this.addParam(new UpdateParam(this, field, field2));
		return this;
	}
	
	public UpdateSet paramIncrease(EntityField field, Object value){
		UpdateParam updateParam = new UpdateParam(this);
		updateParam.sqlParam(field.getFieldName(), " = ", field.getFieldName() + "+" + value);
		this.addParam(updateParam);
		return this;
	}
	
	public UpdateSet sqlParam(Object...sqlPart){
		UpdateParam updateParam = new UpdateParam(this);
		updateParam.sqlParam(sqlPart);
		this.addParam(updateParam);		
		return this;
	}
	
	protected UpdateSet addParam(UpdateParam param) {
		if (this.params == null) {
			this.params = new LinkedList<>();
		}
		this.params.add(param);
		return this;
	}
	
	
	
	
	//------------------------------
	
	public List<UpdateParam> getParams() {
		return params;
	}
	
	public Where<UpdateSet, UpdateSet> getWhere() {
		return where;
	}
	
	public Update getUpdate() {
		return update;
	}

	@Override
	public WhereAble<UpdateSet, UpdateSet> getWhereAble() {
		return this;
	}

	@Override
	public ExecuteAble getExecuteAble() {
		return update;
	}
	
	public void setLimit(LimitParam limit) {
		this.limit = limit;
	}

	public LimitParam getLimit() {
		return this.limit;
	}
	
}
