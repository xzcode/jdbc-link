package com.xzcode.jdbclink.core.sql.param;

import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.sql.param.Param.TypeConstant;
import com.xzcode.jdbclink.core.sql.update.UpdateSet;
import com.xzcode.jdbclink.core.util.ParamUtil;


/**
 * update条件参数
 * 
 * 
 * @author zai
 * 2017-05-27
 */
public class UpdateParam{
	
	
	protected EntityField field;
	
	protected EntityField field2;
	
	protected Object val;
	
	protected Boolean isSatisfy = true;
	
	protected UpdateSet set;
	
	protected String sqlpart;
	
	protected int type = 1; //参数类型，1 EntityField， 2 sqlpart
	
	/**
	 * 参数类型常量
	 * 
	 * @author zai
	 * 2018-09-10 17:20:57
	 */
	public static interface TypeConstant{
		
		int ENTITY_FIELD = 1;
		
		int SQL_PART = 2;
	}
	
	public UpdateParam(UpdateSet set, EntityField field, Object val) {
		this.field = field;
		this.val = val;
		this.set = set;
	}
	
	public UpdateParam(UpdateSet set, EntityField field, EntityField field2) {
		this.field = field;
		this.field2 = field2;
		this.set = set;
	}
	
	public UpdateParam(UpdateSet set, boolean isSatisfy) {
		this.set = set;
		this.isSatisfy = isSatisfy;
	}
	
	public UpdateParam(UpdateSet set) {
		this.set = set;
	}
	
	public UpdateSet sqlParam(Object...sqlPart) {
		this.type = TypeConstant.SQL_PART;
		this.sqlpart = ParamUtil.getSqlParam(sqlPart);
		return this.set;
	}
	
	
	
	public UpdateSet param(EntityField field, Object value) {
			this.field = field;
			this.val = value;
			return this.set;
	}
	
	
	public Boolean getIsSatisfy() {
		return isSatisfy;
	}
	
	public EntityField getField() {
		return field;
	}
	
	public Object getVal() {
		return val;
	}
	
	public UpdateSet getSet() {
		return set;
	}
	
	public EntityField getField2() {
		return field2;
	}
	
	public void setField2(EntityField field2) {
		this.field2 = field2;
	}
	

	

	
	
	
	

}
