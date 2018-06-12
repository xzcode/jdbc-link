package com.sourcemuch.commons.jdbclink.core.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;
import com.sourcemuch.commons.jdbclink.core.cache.IEntityInfoCache;
import com.sourcemuch.commons.jdbclink.core.pool.string.StringBuilderPool;
import com.sourcemuch.commons.jdbclink.core.util.ShowSqlUtil;

public class Insert extends AbstractCommon{
	
	protected EntityInfo entityInfo;

	
	public Insert(Class<?> clazz, JdbcTemplate jdbcTemplate, StringBuilderPool stringBuilderPool, IEntityInfoCache entityInfoCache) {
		this.stringBuilderPool = stringBuilderPool;
		this.jdbcTemplate = jdbcTemplate;
		this.entityInfoCache = entityInfoCache;
		this.entityInfo = entityInfoCache.getEntityInfo(clazz);
	}
	
	/**
	 * 插入数据
	 * @param record 实体对象
	 * @param injectAutoIncrementId 是否注入自增序列id
	 * @return
	 * @throws Exception
	 * 
	 * @author zai
	 * 2017-06-12
	 */
	public int insert(Object record, boolean injectAutoIncrementId) {
		try {

			this.entityInfo = entityInfoCache.getEntityInfo(record.getClass());
			StringBuilder sql = stringBuilderPool.get();
			StringBuilder valuesSql = stringBuilderPool.get();
			Class<?> clazz = record.getClass();
			
			List<Object> args = new ArrayList<>(entityInfo.getColumns().size());
			
			sql.append(" insert into ").append(entityInfo.getTable());
			sql.append(" ( ");
			for (int i = 0; i < entityInfo.getProps().size(); i++) {
				
				Field field = clazz.getDeclaredField(entityInfo.getProps().get(i));
				
				field.setAccessible(true);
				if (field.get(record) != null) {
					args.add(field.get(record));
					sql.append(entityInfo.getColumns().get(i)).append(",");
					
					valuesSql.append("?,");
				}
			}
			
			sql.setLength(sql.length() - 1);
			
			valuesSql.setLength(valuesSql.length() - 1);
			
			sql.append(" ) ")
			.append(" values ")
			.append(" ( ")
			.append(valuesSql.toString())
			.append(" ) ");
			
			stringBuilderPool.returnOject(valuesSql);
			
			String sqlStr = sql.toString();
			
			stringBuilderPool.returnOject(sql);
			
			ShowSqlUtil.debugSqlAndParams(sqlStr, args);
			
			PreparedStatementCreator statement = new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement prepareStatement = con.prepareStatement(sqlStr, Statement.RETURN_GENERATED_KEYS);
					
					for (int i = 1; i <= args.size(); i++) {
						prepareStatement.setObject(i, args.get(i - 1));
					}
					
					return prepareStatement;
				}
			};
			
						
			//注入自增id
			if (injectAutoIncrementId) {
				
				GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
				int result = this.jdbcTemplate.update(statement, keyHolder);
				
				//获取自增序列
				Long uid = keyHolder.getKey().longValue();
				//注入uid
				Field idField = record.getClass().getDeclaredField(entityInfo.getId());
				if (idField != null) {
					idField.setAccessible(true);
					if (idField.getType() == Integer.class) {
						idField.set(record, uid.intValue());
					}else {
						idField.set(record, uid);						
					}
				}
				
				return result;
				
			}else{
				return this.jdbcTemplate.update(statement);
			}
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
