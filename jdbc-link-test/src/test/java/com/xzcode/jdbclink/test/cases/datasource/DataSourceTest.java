package com.xzcode.jdbclink.test.cases.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.xzcode.jdbclink.test.DataSourceFactory;

public class DataSourceTest {
	
	@Test
	public void test01() throws SQLException {
		DataSource dataSource = DataSourceFactory.getDataSource();
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}

}
