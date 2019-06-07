package com.xzcode.jdbclink.test.jdbctemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.core.format.DefaultKeyFormatter;
import com.xzcode.jdbclink.core.format.DefaultValueFormatter;
import com.xzcode.jdbclink.core.sql.resultset.ListMapRowMapper;
import com.xzcode.jdbclink.test.DataSourceFactory;

public class JdbcTemplateTest {
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void init() {
		jdbcTemplate = new JdbcTemplate(DataSourceFactory.getDataSource());
	}
	
	@Test
	public void jdbcTemplateTest01() throws SQLException {
		
		List<Map<String, Object>> query = jdbcTemplate.query("select * from product", new ListMapRowMapper(new DefaultKeyFormatter(), new DefaultValueFormatter()));
		System.out.println(query);
	}

}
