package com.xzcode.jdbclink.test.entity;

import com.xzcode.jdbclink.core.annotations.Column;
import com.xzcode.jdbclink.core.annotations.Id;
import com.xzcode.jdbclink.core.annotations.Table;
import com.xzcode.jdbclink.core.annotations.Entity;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.entity.model.EntityField;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 学生表 实体类
 * 
 * @author JdbcLinkGenerator
 * 2019-06-07 16:18:35
 */
@Entity
@Table(database = Student.__DATABASE_NAME__, name = Student.__TABLE_NAME__, alias = Student.__TABLE_NAME__)
public class Student implements IEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 所在数据库名称
	 */
	public static final String __DATABASE_NAME__ = "jdbc-link-test2";
	
	/**
	 * 学生表 表名
	 */
	public static final String __TABLE_NAME__ = "student";
	
	/**
	 * 表示所有列
	 */
	public static final EntityField ALL_ = new EntityField("*", "*", Student.__TABLE_NAME__);

	//列名常量
	
	/**
	 * 主键
	 */
	public static final EntityField UID = new EntityField("uid", "uid", Student.__TABLE_NAME__);
	
	/**
	 * 姓名
	 */
	public static final EntityField NAME = new EntityField("name", "name", Student.__TABLE_NAME__);
	
	/**
	 * 年龄
	 */
	public static final EntityField SEX = new EntityField("sex", "sex", Student.__TABLE_NAME__);
	
	/**
	 * 所在班级id
	 */
	public static final EntityField CLASS_ID = new EntityField("class_id", "classId", Student.__TABLE_NAME__);
	
	
	
	


	/**
	 * 主键
	 */
	@Id
	@Column(name = "uid")
	private Long uid;
	
	/**
	 * 姓名
	 */
	
	@Column(name = "name")
	private String name;
	
	/**
	 * 年龄
	 */
	
	@Column(name = "sex")
	private Integer sex;
	
	/**
	 * 所在班级id
	 */
	
	@Column(name = "class_id")
	private Long classId;
	



	
	
	/**
	 * 主键
	 */
	public Long getUid() {
		return this.uid;	
	}
	
	/**
	 * 主键
	 */
	public Student setUid(Long uid) {
		this.uid = uid;
		return this;	
	}
	
	
	
	/**
	 * 姓名
	 */
	public String getName() {
		return this.name;	
	}
	
	/**
	 * 姓名
	 */
	public Student setName(String name) {
		this.name = name;
		return this;	
	}
	
	
	
	/**
	 * 年龄
	 */
	public Integer getSex() {
		return this.sex;	
	}
	
	/**
	 * 年龄
	 */
	public Student setSex(Integer sex) {
		this.sex = sex;
		return this;	
	}
	
	
	
	/**
	 * 所在班级id
	 */
	public Long getClassId() {
		return this.classId;	
	}
	
	/**
	 * 所在班级id
	 */
	public Student setClassId(Long classId) {
		this.classId = classId;
		return this;	
	}
	
	
	

}
