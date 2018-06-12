package com.sourcemuch.commons.jdbclink.core.sql.param;

import com.sourcemuch.commons.jdbclink.core.sql.update.UpdateSet;


/**
 * update条件参数
 * 
 * 
 * @author zai
 * 2017-05-27
 */
public class UpdateParam{
	
	
	protected String key;
	
	protected String key2;
	
	protected Object val;
	
	protected Boolean isSatisfy = true;
	
	protected UpdateSet set;
	
	public UpdateParam(UpdateSet set, String key, Object val) {
		this.key = key;
		this.val = val;
		this.set = set;
	}
	
	public UpdateParam(UpdateSet set, String key, String key2) {
		this.key = key;
		this.key2 = key2;
		this.set = set;
	}
	
	public UpdateParam(UpdateSet set, boolean isSatisfy) {
		this.set = set;
		this.isSatisfy = isSatisfy;
	}
	
	public UpdateParam(UpdateSet set) {
		this.set = set;
	}
	
	
	
	/**
	 * 等于
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public UpdateSet param(String key, Object value) {
			this.key = key;
			this.val = value;
			return this.set;
	}
	
	
	public Boolean getIsSatisfy() {
		return isSatisfy;
	}
	
	public String getKey() {
		return key;
	}
	
	public Object getVal() {
		return val;
	}
	
	public UpdateSet getSet() {
		return set;
	}
	
	public String getKey2() {
		return key2;
	}
	
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	

	

	
	
	
	

}
