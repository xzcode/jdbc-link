package com.xzcode.jdbclink.test.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.models.Pager;
import com.xzcode.jdbclink.test.JdbcLinkTestApp;
import com.xzcode.jdbclink.test.entity.Category;
import com.xzcode.jdbclink.test.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcLinkTestApp.class)
public class InsertTest {
	
	@Autowired
	private JdbcLink jdbcLink;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Test
	public void insert01() throws SQLException {
		Product product = new Product();
		product.setName("运动滑板鞋");
		product.setCity("广西");
		product.setPrice(1500L);
		product.setNumber(999);
		product.setCreateDate(new Date());
		
		
		Product product2 = new Product();
		product2.setName("运动滑板鞋2");
		product2.setCity("广西2");
		product2.setPrice(1500L);
		product2.setNumber(9992);
		product2.setCreateDate(new Date());
		
		List<IEntity> arrayList = new ArrayList<>();
		arrayList.add(product);
		arrayList.add(product2);
		
		jdbcLink.batchInsert(arrayList);
				
	}
	

}
