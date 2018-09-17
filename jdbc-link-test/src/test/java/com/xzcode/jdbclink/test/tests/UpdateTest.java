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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcLinkTestApp.class)
public class UpdateTest {
	
	@Autowired
	private JdbcLink jdbcLink;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Test
	public void update01() throws SQLException {
		
		//List<Map<String, Object>> list = jdbcLink.select(Product.class).queryListMap();
		jdbcLink.update(Product.class)
			.set()
				.param(Product.NAME, "xxxxx")
			.where()
				.and().eq(Product.UID, 77L)
			.execute();
		
				
	}
	
	@Test
	public void update02() throws SQLException {
		
		Product product = jdbcLink.select(Product.class).byField(Product.UID, 84L);
		product.setName("8484848");
		jdbcLink.update(product);
				
	}
	
	@Test
	public void update03() throws SQLException {
		
		jdbcLink.update(Product.class)
		.set()
			.sqlParam(Product.NAME, " = ", "okokoko")
		.where()
			.and().eq(Product.UID, 77L)
		.execute();
		
	}
	
	@Test
	public void test04() throws SQLException {
		
		Product product = jdbcLink.select(Product.class).byField(Product.UID, 77l);
		List<Product> list = jdbcLink.select(Product.class).byFieldForList(Product.UID, 77l);
		

		System.out.println(gson.toJson(product));
		System.out.println(gson.toJson(list));
		
	}

}
