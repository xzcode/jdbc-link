package com.xzcode.jdbclink.test.tests;

import java.sql.SQLException;
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
import com.xzcode.jdbclink.core.models.Pager;
import com.xzcode.jdbclink.test.JdbcLinkTestApp;
import com.xzcode.jdbclink.test.entity.Category;
import com.xzcode.jdbclink.test.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcLinkTestApp.class)
public class DeleteTest {
	
	@Autowired
	private JdbcLink jdbcLink;
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Test
	public void delete01() throws SQLException {
		
		jdbcLink.delete(Product.class).where().and().eq(Product.UID, 85).execute();
				
	}
	
	@Test
	public void delete02() throws SQLException {
		
		jdbcLink.delete(85,Product.class);
				
	}
	

}
