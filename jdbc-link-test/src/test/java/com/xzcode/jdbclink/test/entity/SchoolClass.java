package com.xzcode.jdbclink.test.entity;

import com.xzcode.jdbclink.core.annotations.Column;
import com.xzcode.jdbclink.core.annotations.Id;
import com.xzcode.jdbclink.core.annotations.Table;
import com.xzcode.jdbclink.core.annotations.Entity;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.entity.model.EntityField;

import java.lang.Long;
import java.lang.String;

/**
 * 班级表 实体类
 * 
 * @author JdbcLinkGenerator
 * 2019-06-07 16:20:20
 */
@Entity
@Table(database = SchoolClass.__DATABASE_NAME__, name = SchoolClass.__TABLE_NAME__, alias = SchoolClass.__TABLE_NAME__)
public class SchoolClass implements IEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 所在数据库名称
	 */
	public static final String __DATABASE_NAME__ = "jdbc-link-test";
	
	/**
	 * 班级表 表名
	 */
	public static final String __TABLE_NAME__ = "school_class";
	
	/**
	 * 表示所有列
	 */
	public static final EntityField ALL_ = new EntityField("*", "*", SchoolClass.__TABLE_NAME__);

	//列名常量
	
	/**
	 * 主键
	 */
	public static final EntityField UID = new EntityField("uid", "uid", SchoolClass.__TABLE_NAME__);
	
	/**
	 * 班级名称
	 */
	public static final EntityField CLASS_NAME = new EntityField("class_name", "className", SchoolClass.__TABLE_NAME__);
	
	
	
	


	/**
	 * 主键
	 */
	@Id
	@Column(name = "uid")
	private Long uid;
	
	/**
	 * 班级名称
	 */
	
	@Column(name = "class_name")
	private String className;
	



	
	
	/**
	 * 主键
	 */
	public Long getUid() {
		return this.uid;	
	}
	
	/**
	 * 主键
	 */
	public SchoolClass setUid(Long uid) {
		this.uid = uid;
		return this;	
	}
	
	
	
	/**
	 * 班级名称
	 */
	public String getClassName() {
		return this.className;	
	}
	
	/**
	 * 班级名称
	 */
	public SchoolClass setClassName(String className) {
		this.className = className;
		return this;	
	}
	
	
	

}
