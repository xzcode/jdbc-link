package com.sourcemuch.commons.jdbclink.core.sql.interfaces;

import com.sourcemuch.commons.jdbclink.core.sql.limit.LimitParam;

public interface LimitAble<T> extends LimitSizeAble<T>{
	
	
	public default T limitStarts(Integer starts, Integer size) {
		return setLimitParam(new LimitParam(starts, size));
	}
	
	public default T limit(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}
		return setLimitParam(new LimitParam((pageNo - 1) * pageSize, pageNo, pageSize));
	}
	
	
	public default T limit() {
		return setLimitParam(new LimitParam(0, 1, 10));
	}
	
	/*public default T limitStarts(Integer starts, Integer size) {
		return (T) this.getThis().limit(starts, size);
	}

	public default T limit(Integer size) {
		return (T) this.getThis().limit(size);
	}


	public default T limit(Integer pageNo, Integer pageSize){
		return (T) this.getThis().limit(pageNo, pageSize);
	}
	
	public default T limit(){
		return (T) this.getThis().limit(1, 10);
	}*/
}
