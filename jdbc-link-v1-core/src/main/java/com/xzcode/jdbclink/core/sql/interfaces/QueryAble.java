package com.xzcode.jdbclink.core.sql.interfaces;

import java.util.List;
import java.util.Map;

import com.xzcode.jdbclink.core.format.KeyFormatter;
import com.xzcode.jdbclink.core.format.ValueFormatter;
import com.xzcode.jdbclink.core.models.Pager;

public interface QueryAble<T> extends GetSelectAble<T>{
	
	
	public default Pager<Map<String, Object>> pageListMap(KeyFormatter keyFormatter, ValueFormatter valueFormatter) {
		return this.getSelect().pageListMap(keyFormatter, valueFormatter);
	}
	
	
	/**
	 * 获取单行单列的一个数据
	 * @param clazz
	 * @return
	 * 
	 * @author zai
	 * 2017-06-14
	 */
	public default <E> E queryObject(Class<E> clazz) {
		return this.getSelect().queryObject(clazz);
	}
	
	/**
	 * 获取实体对象
	 * @return
	 * 
	 * @author zai
	 * 2017-06-14
	 */
	public default T queryEntity() {
		return this.getSelect().queryEntity();
	}


	
	public default List<T> queryList() {
		return this.getSelect().queryList();
	}


	


	
	public default Map<String, Object> queryMap() {
		return this.getSelect().queryMap();
	}


	

	
	public default Pager<T> pageEntity() {
		return this.getSelect().pageEntity();
	}


	
	public default List<Map<String, Object>> queryListMap() {
		return this.getSelect().queryListMap();
	}


	
	public default Pager<Map<String, Object>> pageListMap() {
		return this.getSelect().pageListMap();
	}

	public default <F> List<F> queryFirstColumn(Class<F> f){
		return this.getSelect().queryFirstColumn(f);
	}

	public default List<Object> queryFirstColumn(){
		return this.getSelect().queryFirstColumn();
	}

}
