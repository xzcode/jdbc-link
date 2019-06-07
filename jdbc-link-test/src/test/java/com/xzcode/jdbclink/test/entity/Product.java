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
import java.lang.Integer;

/**
 * 产品 实体类
 * 
 * @author JdbcLinkGenerator
 * 2019-06-07 15:42:23
 */
@Entity
@Table(database = Product.__DATABASE_NAME__, name = Product.__TABLE_NAME__, alias = Product.__TABLE_NAME__)
public class Product implements IEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 所在数据库名称
	 */
	public static final String __DATABASE_NAME__ = "jdbc-link-test";
	
	/**
	 * 产品 表名
	 */
	public static final String __TABLE_NAME__ = "product";
	
	/**
	 * 表示所有列
	 */
	public static final EntityField ALL_ = new EntityField("*", "*", Product.__TABLE_NAME__);

	//列名常量
	
	/**
	 * 主键
	 */
	public static final EntityField UID = new EntityField("uid", "uid", Product.__TABLE_NAME__);
	
	/**
	 * 分类
	 */
	public static final EntityField CATEGORY_ID = new EntityField("category_id", "categoryId", Product.__TABLE_NAME__);
	
	/**
	 * 名称
	 */
	public static final EntityField NAME = new EntityField("name", "name", Product.__TABLE_NAME__);
	
	/**
	 * 城市名称
	 */
	public static final EntityField CITY = new EntityField("city", "city", Product.__TABLE_NAME__);
	
	/**
	 * 价格
	 */
	public static final EntityField PRICE = new EntityField("price", "price", Product.__TABLE_NAME__);
	
	/**
	 * 库存量
	 */
	public static final EntityField NUMBER = new EntityField("number", "number", Product.__TABLE_NAME__);
	
	/**
	 * 图片
	 */
	public static final EntityField PICTURE = new EntityField("picture", "picture", Product.__TABLE_NAME__);
	
	/**
	 * 创建日期
	 */
	public static final EntityField CREATE_DATE = new EntityField("create_date", "createDate", Product.__TABLE_NAME__);
	
	
	
	


	/**
	 * 主键
	 */
	@Id
	@Column(name = "uid")
	private Long uid;
	
	/**
	 * 分类
	 */
	
	@Column(name = "category_id")
	private Long categoryId;
	
	/**
	 * 名称
	 */
	
	@Column(name = "name")
	private String name;
	
	/**
	 * 城市名称
	 */
	
	@Column(name = "city")
	private String city;
	
	/**
	 * 价格
	 */
	
	@Column(name = "price")
	private Long price;
	
	/**
	 * 库存量
	 */
	
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 图片
	 */
	
	@Column(name = "picture")
	private String picture;
	
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
	public Product setUid(Long uid) {
		this.uid = uid;
		return this;	
	}
	
	
	
	/**
	 * 分类
	 */
	public Long getCategoryId() {
		return this.categoryId;	
	}
	
	/**
	 * 分类
	 */
	public Product setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
		return this;	
	}
	
	
	
	/**
	 * 名称
	 */
	public String getName() {
		return this.name;	
	}
	
	/**
	 * 名称
	 */
	public Product setName(String name) {
		this.name = name;
		return this;	
	}
	
	
	
	/**
	 * 城市名称
	 */
	public String getCity() {
		return this.city;	
	}
	
	/**
	 * 城市名称
	 */
	public Product setCity(String city) {
		this.city = city;
		return this;	
	}
	
	
	
	/**
	 * 价格
	 */
	public Long getPrice() {
		return this.price;	
	}
	
	/**
	 * 价格
	 */
	public Product setPrice(Long price) {
		this.price = price;
		return this;	
	}
	
	
	
	/**
	 * 库存量
	 */
	public Integer getNumber() {
		return this.number;	
	}
	
	/**
	 * 库存量
	 */
	public Product setNumber(Integer number) {
		this.number = number;
		return this;	
	}
	
	
	
	/**
	 * 图片
	 */
	public String getPicture() {
		return this.picture;	
	}
	
	/**
	 * 图片
	 */
	public Product setPicture(String picture) {
		this.picture = picture;
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
	public Product setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;	
	}
	
	
	

}
