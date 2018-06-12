package com.sourcemuch.commons.jdbclink.core.sql.param;

import com.sourcemuch.commons.jdbclink.core.sql.Select;
import com.sourcemuch.commons.jdbclink.core.sql.interfaces.Satisfy;


/**
 * 条件参数
 * @author zai
 * @param <T>
 * 
 * 2017-06-04 21:24:25
 */
public class Param<T> {
	
	protected T t;
	
	protected String connect;// and 或 or
	
	protected String tableAlias = "";
	
	protected String tableAlias2 = "";
	
	protected String key;
	
	protected String key2;
	
	protected String tag;
	
	protected Object val;
	
	protected Object val2;
	
	protected Boolean isSatisfy = true;
	
	protected Select<T> select;
	
	protected Object[] values;
	
	public Param(T t, String key, Object val) {
		this.key = key;
		this.val = val;
		this.t = t;
	}
	
	public Param(T t) {
		this.t = t;
	}
	
	public Param(String connect, T t) {
		this.connect = connect;
		this.t = t;
	}
	
	public Param(String connect, Boolean isSatisfy, T t) {
		this.connect = connect;
		this.t = t;
		this.isSatisfy = isSatisfy;
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
	public T eq(String key, Object value) {
			this.key = key;
			this.val = value;
			this.tag = "=";
			return this.t;
	}
	
	public T eqKey(String key, String key2) {
		this.key = key;
		this.key2 = key2;
		this.tag = "=";
		return this.t;
	}
	
	
	public T eq(String tableAlias, String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.val = value;
		this.tag = "=";
		return this.t;
	}
	
	public T eq(String tableAlias, String key, String tableAlias2, String key2) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.tableAlias2 = tableAlias2;
		this.key2 = key2;
		this.tag = "=";
		return this.t;
	}
	
	
	public T eq(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = "=";
		return this.t;
	}
	
	public T eq(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.select = select;
		this.tag = "=";
		return this.t;
	}
	
	/**
	 * 小于
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T lt(String key, Object value) {
			this.key = key;
			this.val = value;
			this.tag = "<";
			return this.t;
	}
	
	public T ltKey(String key, String key2) {
		this.key = key;
		this.key2 = key2;
		this.tag = "<";
		return this.t;
}
	
	public T lt(String tableAlias,String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.val = value;
		this.tag = "<";
		return this.t;
	}
	
	public T lt(String tableAlias, String key, String tableAlias2, String key2) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.tableAlias2 = tableAlias2;
		this.key2 = key2;
		this.tag = "<";
		return this.t;
	}
	
	public T lt(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = "<";
		return this.t;
	}
	
	public T lt(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.select = select;
		this.tag = "<";
		return this.t;
	}
	
	/**
	 * 大于
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T gt(String key, Object value) {
			this.key = key;
			this.val = value;
			this.tag = ">";
			return this.t;
	}
	
	public T gtKey(String key, String key2) {
		this.key = key;
		this.key2 = key2;
		this.tag = ">";
		return this.t;
	}
	
	public T gt(String tableAlias, String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.val = value;
		this.tag = ">";
		return this.t;
	}
	
	public T gt(String tableAlias, String key, String tableAlias2, String key2) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.tableAlias2 = tableAlias2;
		this.key2 = key2;
		this.tag = ">";
		return this.t;
	}
	
	public T gt(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = ">";
		return this.t;
	}
	
	/**
	 * 大于等于
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T gtEq(String key, Object value) {
			this.key = key;
			this.val = value;
			this.tag = ">=";
			return this.t;
	}
	
	public T gtEqKey(String key, String key2) {
		this.key = key;
		this.key2 = key2;
		this.tag = ">=";
		return this.t;
	}
	
	public T gtEq(String tableAlias,String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.val = value;
		this.tag = ">=";
		return this.t;
	}
	
	public T gtEq(String tableAlias, String key, String tableAlias2, String key2) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.tableAlias2 = tableAlias2;
		this.key2 = key2;
		this.tag = ">=";
		return this.t;
	}
	
	public T gtEq(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = ">=";
		return this.t;
	}
	
	public T gtEq(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.select = select;
		this.tag = ">=";
		return this.t;
	}
	
	/**
	 * 小于等于
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T ltEq(String key, Object value) {
			this.key = key;
			this.val = value;
			this.tag = "<=";
			return this.t;
	}
	
	public T ltEqKey(String key, String key2) {
		this.key = key;
		this.key2 = key2;
		this.tag = "<=";
		return this.t;
	}
	
	public T ltEq(String tableAlias,String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.val = value;
		this.tag = "<=";
		return this.t;
	}
	
	public T ltEq(String tableAlias, String key, String tableAlias2, String key2) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.tableAlias2 = tableAlias2;
		this.key2 = key2;
		this.tag = "<=";
		return this.t;
	}
	
	public T ltEq(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = "<=";
		return this.t;
	}
	
	public T ltEq(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.select = select;
		this.tag = "<=";
		return this.t;
	}
	
	
	/**
	 * 普通 like，value不做特殊处理
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T like(String key, Object value) {
			this.key = key;
			this.val = value;
			this.tag = "like";
		return this.t;
	}
	
	public T like(String tableAlias,String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.val = value;
		this.tag = "like";
	return this.t;
	}
	
	
	public T like(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = "like";
		return this.t;
	}
	
	public T like(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.select = select;
		this.tag = "like";
		return this.t;
	}
	
	/**
	 * 自动增加'%'包裹查询值  -- like '%value%'
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T fuzzyLike(String key, Object value) {
		this.key = key;
		this.val = "%" + value + "%";
		this.tag = "like";
		return this.t;
	}
	
	public T fuzzyLike(String tableAlias,String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.val = "%" + value + "%";
		this.tag = "like";
		return this.t;
	}
	
	
	/**
	 * 使用locate代替like，提高性能
	 * @param key
	 * @param value
	 * @return
	 */
	public T locate(String key, Object value) {
		this.key = key;
		//this.val = "locate('" + value +"', "+ key +") > 0";
		this.val = value;
		this.tag = "locate";
		return this.t;
	}
	
	public T locate(String tableAlias,String key, Object value) {
		this.tableAlias = tableAlias;
		this.key = key;
		//this.val = "locate('" + value +"', "+ key +") > 0";
		this.val = value;
		this.tag = "locate";
		return this.t;
	}
	
	
	/**
	 * between 连接条件
	 * @param key
	 * @param betweenFrom
	 * @param betweenTo
	 * @return
	 * 
	 * @author zai
	 * 2017-05-23
	 */
	public T between(String key, Object betweenFrom, Object betweenTo) {
			this.key = key;
			this.tag = "between";
			this.val = betweenFrom;
			this.val2 = betweenTo;
		return this.t;
	}
	
	
	public T between(String tableAlias,String key, Object betweenFrom, Object betweenTo) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.tag = "between";
		this.val = betweenFrom;
		this.val2 = betweenTo;
	return this.t;
}
	
	/**
	 * in 连接条件（多重载）
	 * @param key
	 * @param values
	 * @return
	 */
	public T in(String key, Object[] values) {
			this.key = key;
			//this.val = "(" + StringUtils.join(values, ",") + ")";
			this.values = values;
			this.tag = "in";
			return this.t;
	}
	
	public T in(String tableAlias,String key, Object[] values) {
		this.tableAlias = tableAlias;
		this.key = key;
		//this.val = "(" + StringUtils.join(values, ",") + ")";
		this.values = values;
		this.tag = "in";
		return this.t;
	}
	
	
	/**
	 * in 连接条件（多重载）
	 * @param key
	 * @param select
	 * @return
	 */
	public T in(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = "in";
		return this.t;
	}
	
	public T in(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.select = select;
		this.tag = "in";
		return this.t;
	}
	
	
	/**
	 * not in 连接条件（多重载）
	 * @param key
	 * @param values
	 * @return
	 */
	public T notIn(String key, Object[] values) {
		this.key = key;
		//this.val = "('" + StringUtils.join(values, "','") +"')";
		this.values = values;
		this.tag = "not in";
		return this.t;
	}
	
	public T notIn(String tableAlias,String key, Object[] values) {
		this.tableAlias = tableAlias;
		this.key = key;
		//this.val = "('" + StringUtils.join(values, "','") +"')";
		this.values = values;
		this.tag = "not in";
		return this.t;
	}
	
	/**
	 * not in 连接条件（多重载）
	 * @param key
	 * @param select
	 * @return
	 */
	public T notIn(String key, Select<T> select) {
		this.key = key;
		this.select = select;
		this.tag = "not in";
		return this.t;
	}
	
	
	public T notIn(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.key = key;
		this.select = select;
		this.tag = "not in";
		return this.t;
	}
	
	public T exists(String key, Select<T> select) {
		this.select = select;
		this.tag = "exists";
		return this.t;
	}
	
	public T exists(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.select = select;
		this.tag = "exists";
		return this.t;
	}
	
	public T notExists(String key, Select<T> select) {
		this.select = select;
		this.tag = "not exists";
		return this.t;
	}
	
	public T notExists(String tableAlias,String key, Select<T> select) {
		this.tableAlias = tableAlias;
		this.select = select;
		this.tag = "not exists";
		return this.t;
	}
	
	
	public Param<T> when(Boolean isSatisfy) {
		this.isSatisfy = isSatisfy;
		return this;
	}
	
	public Param<T> when(Satisfy satisfy) {
		this.isSatisfy = satisfy.isSatisfy();
		return this;
	}
	
	
	//----------------------


	public String getConnect() {
		return connect;
	}

	public void setConnect(String connect) {
		this.connect = connect;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

	public Object getVal2() {
		return val2;
	}

	public void setVal2(Object val2) {
		this.val2 = val2;
	}

	public Boolean getIsSatisfy() {
		return isSatisfy;
	}

	
	
	
	public Object[] getValues() {
		return values;
	}
	
	public void setValues(Object[] values) {
		this.values = values;
	}
	
	public Select<T> getSelect() {
		return select;
	}
	
	
	public void setIsSatisfy(Boolean isSatisfy) {
		this.isSatisfy = isSatisfy;
	}
	
	public String getTableAlias() {
		return tableAlias;
	}
	
	public void setTableAlias(String tableAlias) {
		if (tableAlias != null) {
			this.tableAlias = tableAlias;			
		}
	}
	public String getKey2() {
		return key2;
	}
	
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	
	public String getTableAlias2() {
		return tableAlias2;
	}
	
	public void setTableAlias2(String tableAlias2) {
		if (tableAlias2 != null) {
			this.tableAlias2 = tableAlias2;			
		}
	}
	

}
