package com.xzcode.jdbclink.core.sql.execution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SqlExecution{
	
	private String sql;
	private Object[] args;
	private JdbcTemplate jdbcTemplate;
	
	public SqlExecution(String sql, Object[] args, JdbcTemplate jdbcTemplate) {
		super();
		this.sql = sql;
		this.args = args;
		this.jdbcTemplate = jdbcTemplate;
	}


	public <E> E queryObject(Class<E> clazz) {
		try {
			
			return this.jdbcTemplate.queryForObject(sql, clazz,args);
			
		} catch (EmptyResultDataAccessException  e) {
			return null;
		}
	}

	
	public Map<String, Object> queryMap() {
		return this.jdbcTemplate.queryForMap(sql, args);
	}


	
	public List<Map<String, Object>> queryListMap() {
		return this.jdbcTemplate.queryForList(sql, args);
	}

	
	
	public <F> List<F> queryFirstColumn(Class<F> f) {
		return jdbcTemplate.query(sql, args, new ResultSetExtractor<List<F>>(){

			@SuppressWarnings("unchecked")
			@Override
			public List<F> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<F> list = new LinkedList<>();
				
				while (rs.next()) {
					if (f == String.class) {
						list.add((F) String.valueOf(rs.getObject(1)));
					}else {
						list.add((F) rs.getObject(1));
					}
				}
				return list;
			}
			
		});
	}

}
