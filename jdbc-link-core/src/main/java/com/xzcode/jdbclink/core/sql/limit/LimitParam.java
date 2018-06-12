package com.xzcode.jdbclink.core.sql.limit;

public class LimitParam {
	
	private Integer starts;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	public LimitParam() {
	}

	public LimitParam(Integer starts, Integer size) {
		super();
		this.starts = starts;
		this.pageSize = size;
	}
	
	

	public LimitParam(Integer starts, Integer pageNo, Integer pageSize) {
		super();
		this.starts = starts;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Integer getStarts() {
		return starts;
	}

	public void setStarts(Integer starts) {
		this.starts = starts;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	
	
	
}
