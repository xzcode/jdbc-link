package com.xzcode.jdbclink.test.generator;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.codegen.JdbcLinkGenerator;
import com.xzcode.jdbclink.codegen.config.JdbcLinkEntityGeneratorConfig;
import com.xzcode.jdbclink.test.DataSourceFactory;

public class CodeGenTest {
	
private JdbcTemplate jdbcTemplate;
	
	@Before
	public void init() {
		jdbcTemplate = new JdbcTemplate(DataSourceFactory.getDataSource());
	}
	
	@Test
	public void genCodeTest01() throws SQLException {
		JdbcLinkEntityGeneratorConfig config = new JdbcLinkEntityGeneratorConfig();
		config.setEntityDatabaseName("jdbc-link-test");
		config.setDbUrl("jdbc:mysql://common.host:8306/jdbc-link-test?useSSL=false");
		config.setDbUserName("root");
		config.setDbPassword("123456");
		config.setEntityBasicOutputPath("D:/EE_MY/jdbc-link/jdbc-link-test/src/main/java");
		config.setEntityBasicPackage("com.xzcode.jdbclink.test.entity");
		config.setCreateModulePackage(false);
		
		JdbcLinkGenerator generator = new JdbcLinkGenerator(config, jdbcTemplate);
		generator.generateEntities();
		
	}

}
