package com.xzcode.jdbclink.core.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.exception.JdbcLinkRuntimeException;
import com.xzcode.jdbclink.core.models.SqlAndParams;
import com.xzcode.jdbclink.core.util.ShowSqlUtil;

public class Insert{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Insert.class);
	
	protected String database;
	
	protected EntityInfo entityInfo;
	
	protected JdbcLinkConfig config;

	
	public Insert(Class<?> clazz, JdbcLinkConfig config) {
		this.config = config;
		this.entityInfo = config.getEntityInfoCache().getEntityInfo(clazz);
	}
	
	/**
	 * 插入数据
	 * @param entity 实体对象
	 * @param injectAutoIncrementId 是否注入自增序列id
	 * @return
	 * @throws Exception
	 * 
	 * @author zai
	 * 2017-06-12
	 */
	public int insert(IEntity entity, boolean injectAutoIncrementId) {
		try {

			this.entityInfo = config.getEntityInfoCache().getEntityInfo(entity.getClass());
			
			StringBuilder sql = config.getStringBuilderPool().get();
			StringBuilder valuesSql = config.getStringBuilderPool().get();
			
			sql.append(" insert into ");
			sql.append("`").append(entityInfo.getDatabase()).append("`").append(".");
			sql.append("`").append(entityInfo.getTable()).append("`");
			sql.append(" ( ");
			List<EntityFieldInfo> fieldInfos = entityInfo.getFieldInfos();
			List<Object> args = new ArrayList<>(fieldInfos.size());
			
			int fieldInfosSize = fieldInfos.size();
			for (int i = 0; i < fieldInfosSize; i++) {
				
				Field field = fieldInfos.get(i).getField();
				
				field.setAccessible(true);
				if (field.get(entity) != null) {
					args.add(field.get(entity));
					sql
					.append("`")
					.append(fieldInfos.get(i).getColumn())
					.append("`")
					.append(",");
					
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
			
			config.getStringBuilderPool().returnOject(valuesSql);
			
			String sqlStr = sql.toString();
			
			config.getStringBuilderPool().returnOject(sql);
			
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
				int result = this.config.getJdbcTemplate().update(statement, keyHolder);
				
				//获取自增序列
				Long uid = keyHolder.getKey().longValue();
				
				//注入uid
				Field idField = entityInfo.getPrimaryKeyFieldInfo().getField();
				
				if (idField != null) {
					idField.setAccessible(true);
					if (idField.getType() == Integer.class) {
						idField.set(entity, uid.intValue());
					}else {
						idField.set(entity, uid);						
					}
				}
				
				return (int) this.update(sqlStr, args, result);
			}else{
				int result = this.config.getJdbcTemplate().update(statement);
				return (int) this.update(sqlStr, args, result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private Object update(String sql, List<Object> args, Object result) {
		try {
			
			if (LOGGER.isDebugEnabled()) {
				SqlAndParams sqlAndParams = new SqlAndParams();
				sqlAndParams.setSql(sql);
				sqlAndParams.setArgs(args);
				sqlAndParams.addResult(result);
				ShowSqlUtil.debugSqlAndParams(sqlAndParams);
			}
			return result;
			} catch (Exception e) {
				if (LOGGER.isDebugEnabled()) {
					SqlAndParams sqlAndParams = new SqlAndParams();
					sqlAndParams.setSql(sql);
					sqlAndParams.setArgs(args);
					ShowSqlUtil.debugSqlAndParams(sqlAndParams);
				}
				
				throw new JdbcLinkRuntimeException(e);
			}
	}
	
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}

}
