package com.xzcode.jdbclink.test.entity;

import com.xzcode.jdbclink.core.annotations.Column;
import com.xzcode.jdbclink.core.annotations.Id;
import com.xzcode.jdbclink.core.annotations.Table;
import com.xzcode.jdbclink.core.annotations.Entity;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.entity.model.EntityField;

import java.lang.Long;
import java.util.Date;
import java.lang.String;

/**
 *  实体类
 * 
 * @author JdbcLinkGenerator
 * 2018-09-15 00:26:12
 */
@Entity
@Table(name = Category.__TABLE_NAME__, alias = Category.__TABLE_NAME__)
public class Category implements IEntity {

	/**
	 *  表名
	 */
	public static final String __TABLE_NAME__ = "category";
	
	/**
	 * 表示所有列
	 */
	public static final EntityField ALL_ = new EntityField("*", "*", Category.__TABLE_NAME__);

	//列名常量
	
	/**
	 * 主键
	 */
	public static final EntityField UID = new EntityField("uid", "uid", Category.__TABLE_NAME__);
	
	/**
	 * 分类名称
	 */
	public static final EntityField CATEGORY_NAME = new EntityField("category_name", "categoryName", Category.__TABLE_NAME__);
	
	/**
	 * 创建日期
	 */
	public static final EntityField CREATE_DATE = new EntityField("create_date", "createDate", Category.__TABLE_NAME__);
	
	
	
	


	/**
	 * 主键
	 */
	@Id
	@Column(name = "uid")
	private Long uid;
	
	/**
	 * 分类名称
	 */
	
	@Column(name = "category_name")
	private String categoryName;
	
	/**
	 * 创建日期
	 */
	
	@Column(name = "create_date")
	private Date createDate;
	



	
	
	/**
	 * 主键
	 */
	public Long getUid() {
		return this.uid;	
	}
	
	/**
	 * 主键
	 */
	public Category setUid(Long uid) {
		this.uid = uid;
		return this;	
	}
	
	
	
	/**
	 * 分类名称
	 */
	public String getCategoryName() {
		return this.categoryName;	
	}
	
	/**
	 * 分类名称
	 */
	public Category setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		return this;	
	}
	
	
	
	/**
	 * 创建日期
	 */
	public Date getCreateDate() {
		return this.createDate;	
	}
	
	/**
	 * 创建日期
	 */
	public Category setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;	
	}
	
	
	

}
