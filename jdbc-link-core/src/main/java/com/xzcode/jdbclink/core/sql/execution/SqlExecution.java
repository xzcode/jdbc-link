package com.xzcode.jdbclink.core.sql.execution;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.util.ShowSqlUtil;

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
		try {
			
			return this.jdbcTemplate.queryForMap(sql, args);
			
		} catch (EmptyResultDataAccessException  e) {
			return null;
		}
		
	}


	
	public List<Map<String, Object>> queryListMap() {
		try {
			
			return this.jdbcTemplate.queryForList(sql, args);
			
		} catch (EmptyResultDataAccessException  e) {
			return null;
		}
		
	}
	
	/**
	 * 执行update
	 * 
	 * @return
	 * @author zai
	 * 2019-04-01 16:38:11
	 */
	public int executeUpdate() {
		return this.jdbcTemplate.update(sql, args);
	}

	/**
	 * 执行insert
	 * 
	 * @return
	 * @author zai
	 * 2019-04-02 14:16:13
	 */
	public int executeInsert() {
		try {

			ShowSqlUtil.debugSqlAndParams(this.sql, args);
			
			PreparedStatementCreator statement = new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement prepareStatement = con.prepareStatement(sql);
					
					if (args != null) {
						for (int i = 1; i <= args.length; i++) {
							prepareStatement.setObject(i, args[i - 1]);
						}
					}
					
					return prepareStatement;
				}
			};
			
			return this.jdbcTemplate.update(statement);
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
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
