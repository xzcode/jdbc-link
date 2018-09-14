package com.xzcode.jdbclink.core.sql.param;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.sql.Select;
import com.xzcode.jdbclink.core.sql.interfaces.Satisfy;
import com.xzcode.jdbclink.core.util.ParamUtil;


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
	
	protected EntityField field;
	
	protected EntityField field2;
	
	protected String tag;
	
	protected Object val;
	
	protected Object val2;
	
	protected Boolean isSatisfy = true;
	
	protected Select<T> select;
	
	protected Object[] values;
	
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
	
	public Param(T t, EntityField field, Object val) {
		this.field = field;
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
	
	
	public T sqlParam(Object...sqlPart) {
		this.type = TypeConstant.SQL_PART;
		this.sqlpart = ParamUtil.getSqlParam(sqlPart);
		return this.t;
	}
	
	
	
	/**
	 * 等于
	 * @param field
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T eq(EntityField field, Object value) {
			this.field = field;
			this.val = value;
			this.tag = "=";
			this.tableAlias = field.getTableAlias();
			return this.t;
	}
	
	public T eq(EntityField field, EntityField field2) {
		this.field = field;
		this.field2 = field2;
		this.tag = "=";
		this.tableAlias = field.getTableAlias();
		this.tableAlias2 = field2.getTableAlias();
		return this.t;
	}
	
	public T eqfield(EntityField field, EntityField field2) {
		this.field = field;
		this.field2 = field2;
		this.tag = "=";
		this.tableAlias = field.getTableAlias();
		this.tableAlias2 = field2.getTableAlias();
		return this.t;
	}
	
	
	public T eq(String tableAlias, EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.val = value;
		this.tag = "=";
		return this.t;
	}
	
	public T eq(String tableAlias, EntityField field, String tableAlias2, EntityField field2) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.tableAlias2 = tableAlias2;
		this.field2 = field2;
		this.tag = "=";
		return this.t;
	}
	
	public T eq(EntityField field, String tableAlias2, EntityField field2) {
		this.tableAlias = field.getTableAlias();
		this.field = field;
		this.tableAlias2 = tableAlias2;
		this.field2 = field2;
		this.tag = "=";
		return this.t;
	}
	
	public T eq(String tableAlias, EntityField field, EntityField field2) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.tableAlias2 = field2.getTableAlias();
		this.field2 = field2;
		this.tag = "=";
		return this.t;
	}
	
	
	public T eq(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = "=";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T eq(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.select = select;
		this.tag = "=";
		return this.t;
	}
	
	/**
	 * 小于
	 * @param field
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T lt(EntityField field, Object value) {
			this.field = field;
			this.val = value;
			this.tag = "<";
			this.tableAlias = field.getTableAlias();
			return this.t;
	}
	
	public T ltfield(EntityField field, EntityField field2) {
		this.field = field;
		this.field2 = field2;
		this.tag = "<";
		this.tableAlias = field.getTableAlias();
		this.tableAlias2 = field2.getTableAlias();
		return this.t;
}
	
	public T lt(String tableAlias,EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.val = value;
		this.tag = "<";
		return this.t;
	}
	
	public T lt(String tableAlias, EntityField field, String tableAlias2, EntityField field2) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.tableAlias2 = tableAlias2;
		this.field2 = field2;
		this.tag = "<";
		return this.t;
	}
	
	public T lt(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = "<";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T lt(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.select = select;
		this.tag = "<";
		return this.t;
	}
	
	/**
	 * 大于
	 * @param field
	 * @param value
	 * @return
	 * 
	 * @author zai
	 * 2017-05-10
	 */
	public T gt(EntityField field, Object value) {
			this.field = field;
			this.val = value;
			this.tag = ">";
			this.tableAlias = field.getTableAlias();
			return this.t;
	}
	
	public T gtKey(EntityField field, EntityField field2) {
		this.field = field;
		this.field2 = field2;
		this.tag = ">";
		this.tableAlias = field.getTableAlias();
		this.tableAlias2 = field2.getTableAlias();
		return this.t;
	}
	
	public T gt(String tableAlias, EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.val = value;
		this.tag = ">";
		return this.t;
	}
	
	public T gt(String tableAlias, EntityField field, String tableAlias2, EntityField field2) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.tableAlias2 = tableAlias2;
		this.field2 = field2;
		this.tag = ">";
		return this.t;
	}
	
	public T gt(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = ">";
		this.tableAlias = field.getTableAlias();
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
	public T gtEq(EntityField field, Object value) {
			this.field = field;
			this.val = value;
			this.tag = ">=";
			this.tableAlias = field.getTableAlias();
			return this.t;
	}
	
	public T gtEqKey(EntityField field, EntityField field2) {
		this.field = field;
		this.field2 = field2;
		this.tag = ">=";
		this.tableAlias = field.getTableAlias();
		this.tableAlias2 = field2.getTableAlias();
		return this.t;
	}
	
	public T gtEq(String tableAlias,EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.val = value;
		this.tag = ">=";
		return this.t;
	}
	
	public T gtEq(String tableAlias, EntityField field, String tableAlias2, EntityField field2) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.tableAlias2 = tableAlias2;
		this.field2 = field2;
		this.tag = ">=";
		return this.t;
	}
	
	public T gtEq(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = ">=";
		return this.t;
	}
	
	public T gtEq(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T ltEq(EntityField field, Object value) {
			this.field = field;
			this.val = value;
			this.tag = "<=";
			this.tableAlias = field.getTableAlias();
			return this.t;
	}
	
	public T ltEqKey(EntityField field, EntityField field2) {
		this.field = field;
		this.field2 = field2;
		this.tag = "<=";
		this.tableAlias = field.getTableAlias();
		this.tableAlias2 = field2.getTableAlias();
		return this.t;
	}
	
	public T ltEq(String tableAlias,EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.val = value;
		this.tag = "<=";
		return this.t;
	}
	
	public T ltEq(String tableAlias, EntityField field, String tableAlias2, EntityField field2) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.tableAlias2 = tableAlias2;
		this.field2 = field2;
		this.tag = "<=";
		return this.t;
	}
	
	public T ltEq(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = "<=";
		return this.t;
	}
	
	public T ltEq(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T like(EntityField field, Object value) {
		this.field = field;
		this.val = value;
		this.tag = "like";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T like(String tableAlias,EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.val = value;
		this.tag = "like";
		return this.t;
	}
	
	
	public T like(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = "like";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T like(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T fuzzyLike(EntityField field, Object value) {
		this.field = field;
		this.val = "%" + value + "%";
		this.tag = "like";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T fuzzyLike(String tableAlias, EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T locate(EntityField field, Object value) {
		this.field = field;
		//this.val = "locate('" + value +"', "+ key +") > 0";
		this.val = value;
		this.tag = "locate";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T locate(String tableAlias,EntityField field, Object value) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T between(EntityField field, Object betweenFrom, Object betweenTo) {
		this.field = field;
		this.tag = "between";
		this.val = betweenFrom;
		this.val2 = betweenTo;
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	
	public T between(String tableAlias,EntityField field, Object betweenFrom, Object betweenTo) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T in(EntityField field, Object[] values) {
		this.field = field;
		//this.val = "(" + StringUtils.join(values, ",") + ")";
		this.values = values;
		this.tag = "in";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T in(String tableAlias,EntityField field, Object[] values) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T in(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = "in";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T in(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T notIn(EntityField field, Object[] values) {
		this.field = field;
		//this.val = "('" + StringUtils.join(values, "','") +"')";
		this.values = values;
		this.tag = "not in";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T notIn(String tableAlias,EntityField field, Object[] values) {
		this.tableAlias = tableAlias;
		this.field = field;
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
	public T notIn(EntityField field, Select<T> select) {
		this.field = field;
		this.select = select;
		this.tag = "not in";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	
	public T notIn(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.field = field;
		this.select = select;
		this.tag = "not in";
		return this.t;
	}
	
	public T exists(EntityField field, Select<T> select) {
		this.select = select;
		this.tag = "exists";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T exists(String tableAlias,EntityField field, Select<T> select) {
		this.tableAlias = tableAlias;
		this.select = select;
		this.tag = "exists";
		return this.t;
	}
	
	public T notExists(EntityField field, Select<T> select) {
		this.select = select;
		this.tag = "not exists";
		this.tableAlias = field.getTableAlias();
		return this.t;
	}
	
	public T notExists(String tableAlias,EntityField field, Select<T> select) {
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

	public EntityField getField() {
		return field;
	}

	public void setField(EntityField field) {
		this.field = field;
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
	public EntityField getField2() {
		return field2;
	}
	
	public void setField2(EntityField field2) {
		this.field2 = field2;
	}
	
	public String getTableAlias2() {
		return tableAlias2;
	}
	
	public void setTableAlias2(String tableAlias2) {
		if (tableAlias2 != null) {
			this.tableAlias2 = tableAlias2;			
		}
	}

	public String getSqlpart() {
		return sqlpart;
	}

	public void setSqlpart(String sqlpart) {
		this.sqlpart = sqlpart;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	

}
