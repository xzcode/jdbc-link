package com.sourcemuch.commons.jdbclink.core.test;

import java.util.Date;

import com.sourcemuch.commons.jdbclink.core.annotations.Column;
import com.sourcemuch.commons.jdbclink.core.annotations.Entity;
import com.sourcemuch.commons.jdbclink.core.annotations.Id;
import com.sourcemuch.commons.jdbclink.core.annotations.Table;

/**
 * 预约信息 实体类
 * 
 * @author zai
 * 2017-05-26 18:36:43
 */
@Entity
@Table(name = "reservation_info")
public class ReservationInfo {

	/**
	 * 标识
	 */
	@Id
	@Column(name = "uid")
	private Long uid;
	
	/**
	 * 预约用户
	 */
	
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 预约状态0：预约；1：已通知
	 */
	
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 说明
	 */
	
	@Column(name = "remarks")
	private String remarks;
	
	/**
	 * 创建时间
	 */
	
	@Column(name = "create_date")
	private Date createDate;
	
	/**
	 * 创建人
	 */
	
	@Column(name = "create_by")
	private Long createBy;
	
	/**
	 * 更新人
	 */
	
	@Column(name = "update_date")
	private Date updateDate;
	
	/**
	 * 更新者
	 */
	
	@Column(name = "update_by")
	private Long updateBy;
	
	/**
	 * 删除标识0：未删除；1：已删除
	 */
	
	@Column(name = "del_flag")
	private Integer delFlag;
	
	
	public Long getUid() {
		return this.uid;	
	}
	
	public void setUid(Long uid) {
		this.uid = uid;	
	}
	
	
	public Long getUserId() {
		return this.userId;	
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;	
	}
	
	
	public Integer getStatus() {
		return this.status;	
	}
	
	public void setStatus(Integer status) {
		this.status = status;	
	}
	
	
	public String getRemarks() {
		return this.remarks;	
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;	
	}
	
	
	public Date getCreateDate() {
		return this.createDate;	
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;	
	}
	
	
	public Long getCreateBy() {
		return this.createBy;	
	}
	
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;	
	}
	
	
	public Date getUpdateDate() {
		return this.updateDate;	
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;	
	}
	
	
	public Long getUpdateBy() {
		return this.updateBy;	
	}
	
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;	
	}
	
	
	public Integer getDelFlag() {
		return this.delFlag;	
	}
	
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;	
	}

}
