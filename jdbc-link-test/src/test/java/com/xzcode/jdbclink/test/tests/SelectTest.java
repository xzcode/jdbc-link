package com.xzcode.jdbclink.test.tests;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xzcode.jdbclink.core.JdbcLink;
import com.xzcode.jdbclink.core.models.Pager;
import com.xzcode.jdbclink.test.JdbcLinkTestApp;
import com.xzcode.jdbclink.test.entity.Category;
import com.xzcode.jdbclink.test.entity.Product;
import com.xzcode.jdbclink.test.entity.SchoolClass;
import com.xzcode.jdbclink.test.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcLinkTestApp.class)
public class SelectTest {
	
	@Autowired
	private JdbcLink jdbcLink;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Test
	public void test01() throws SQLException {
		
		List<Map<String, Object>> list = jdbcLink.select(Product.class).queryListMap();
		
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
				
	}
	
	@Test
	public void test02() throws SQLException {
		
		List<Map<String, Object>> list = 
				
				jdbcLink.select(Product.class, "p")
				.column("count(*)")
				.column("p", Product.NAME)
				.column("p", Product.CATEGORY_ID)
				.column(Category.CATEGORY_NAME)
				.leftJoin(Category.class)
					.on().eq(Category.UID, "p", Product.CATEGORY_ID)
				.where()
					.and().eq("p", Product.UID, 77l)
				.orderBy("p", Product.UID).asc()
				.queryListMap();
		
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
				
	}
	
	@Test
	public void test03() throws SQLException {
		
		Pager<Map<String, Object>> pager = jdbcLink.select(Product.class)
		.column(Product.NAME, "productName")
		.column(Product.CATEGORY_ID)
		.column(Category.CATEGORY_NAME)
		.columnSql("concat(",Product.NAME, ",", Category.CATEGORY_NAME, ") ccc")
		
		.leftJoin(Category.class)
			.on().eq(Category.UID, Product.CATEGORY_ID)
		.where()
			.and().sqlParam(Product.UID, " = ",77l)
			.and().eq(Product.UID, 77l)
		.orderBy(Product.UID).asc()
		.orderBy(Product.CREATE_DATE).desc()
		.limit(1, 10)
		.pageListMap();

		System.out.println(gson.toJson(pager));
		
	}
	
	@Test
	public void test04() throws SQLException {
		
		Product product = jdbcLink.select(Product.class).byField(Product.UID, 77l);
		List<Product> list = jdbcLink.select(Product.class).byFieldForList(Product.UID, 77l);
		

		System.out.println(gson.toJson(product));
		System.out.println(gson.toJson(list));
		
	}
	
	@Test
	public void test05() throws SQLException {
		
		Pager<Map<String, Object>> pager = jdbcLink.select(Product.class, "p")
		.column("p", Product.ALL_)
		.column(Category.ALL_)
		//.column(Category.CATEGORY_NAME)
		//.columnSql("concat(",Product.NAME, ",", Category.CATEGORY_NAME, ") ccc")
		
		.join(Category.class)
			.on().eq(Category.UID, "p", Product.CATEGORY_ID)
		.where()
			.and().eq("p", Product.UID, 77l)
		.orderBy("p", Product.UID).asc()
		.orderBy("p", Product.CREATE_DATE).desc()
		.limit(1, 10)
		.pageListMap();

		System.out.println(gson.toJson(pager));
		
	}
	
	@Test
	public void test06() throws SQLException {
		
		List<Map<String, Object>> list = jdbcLink.select(Student.class).queryListMap();
		
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
				
	}
	@Test
	public void test07() throws SQLException {
		
		List<Map<String, Object>> list = jdbcLink.select(Student.class)
				.column(Student.ALL_)
				.column(SchoolClass.ALL_)
				.leftJoin(SchoolClass.class)
					.on().eq(SchoolClass.UID, Student.CLASS_ID)
				.queryListMap();
		
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		
	}
	
	
	@Test
	public void test08() throws SQLException {
		
		Pager<Map<String, Object>> pager = jdbcLink.select(Product.class, "p")
		.column("p", Product.ALL_)
		.column(Category.ALL_)
		//.column(Category.CATEGORY_NAME)
		//.columnSql("concat(",Product.NAME, ",", Category.CATEGORY_NAME, ") ccc")
		
		.join(Category.class)
			.on().eq(Category.UID, "p", Product.CATEGORY_ID)
		.where()
			.and().isNotNull("p", Product.UID)
		.orderBy("p", Product.UID).asc()
		.orderBy("p", Product.CREATE_DATE).desc()
		.limit(1, 10)
		.pageListMap();

		System.out.println(gson.toJson(pager));
		
	}

}
