package com.xzcode.jdbclink.test;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

public class DataSourceFactory {
	
	private static final String jdbcUrl = "jdbc:mysql://common.host:8306/jdbc-link-test?useSSL=false";
	private static final String jdbcUsername = "root";
	private static final String jdbcPassword = "123456";
	
	public static DataSource getDataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(jdbcUrl);
		ds.setUsername(jdbcUsername);
		ds.setPassword(jdbcPassword);
		return ds;
	}

}
