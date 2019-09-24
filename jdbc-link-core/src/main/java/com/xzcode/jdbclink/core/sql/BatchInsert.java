package com.xzcode.jdbclink.core.sql;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.util.ShowSqlUtil;

public class BatchInsert {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchInsert.class);
	
	protected JdbcLinkConfig config;
	
	public BatchInsert(JdbcLinkConfig config) {
		this.config = config;
	}
	
	/**
	 * 插入数据
	 * @param record 实体对象
	 * @return
	 * @throws Exception
	 * 
	 * @author zai
	 * 2017-06-12
	 */
	public void batchInsert(List<IEntity> entities) {
		if (entities == null || entities.size() == 0) {
			return;
		}
		
		StringBuilder sql = config.getStringBuilderPool().get();
		StringBuilder valuesSql = config.getStringBuilderPool().get();
		try {
			
			IEntity entity = entities.get(0);
			
			Class<?> clazz = entity.getClass();
			
			EntityInfo entityInfo = config.getEntityInfoCache().getEntityInfo(clazz);
			
			
			sql.append(" insert into ");
			/*sql.append("`").append(entityInfo.getDatabase()).append("`").append(".");*/
			sql.append("`").append(entityInfo.getTable()).append("`");
			
			sql.append(" ( ");
			valuesSql.append("( ");
			
			List<EntityFieldInfo> fieldInfos = entityInfo.getFieldInfos();
			int fieldInfosSize = fieldInfos.size();
			
			for (int i = 0; i < fieldInfosSize; i++) {
				sql.append(fieldInfos.get(i).getColumn()).append(",");
				valuesSql.append("?,");
			}
			
			
			sql.setLength(sql.length() - 1);
			
			sql.append(" ) ").append(" values ");
			
			valuesSql.setLength(valuesSql.length() - 1);
			valuesSql.append(" ),");
			
			sql.append(valuesSql.toString());
			
			valuesSql.setLength(0);
			
			
			sql.setLength(sql.length() - 1);
			
			String sqlStr = sql.toString();
			
			if (LOGGER.isDebugEnabled()) {
				List<List<Object>> args = new ArrayList<>();
				this.config.getJdbcTemplate().batchUpdate(sqlStr, new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {
						IEntity rec = entities.get(index);
						Field field = null;
						List<Object> args2 = new ArrayList<>();
						for (int i = 1; i <= fieldInfosSize; i++) {
							field = fieldInfos.get(i - 1).getField();
							try {
								Object value = field.get(rec);
								args2.add(value);
								ps.setObject(i, value);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
						args.add(args2);
						return;
					}
					@Override
					public int getBatchSize() {
						return entities.size();
					}
				});
				ShowSqlUtil.debugSqlAndParams2(sqlStr, args);
				return;
			}
			
			this.config.getJdbcTemplate().batchUpdate(sqlStr, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int index) throws SQLException {
					IEntity rec = entities.get(index);
					Field field = null;
					for (int i = 1; i <= fieldInfosSize; i++) {
						field = fieldInfos.get(i - 1).getField();
						try {
							ps.setObject(i, field.get(rec));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}
				@Override
				public int getBatchSize() {
					return entities.size();
				}
			});
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			config.getStringBuilderPool().returnOject(valuesSql);
			
			config.getStringBuilderPool().returnOject(sql);
		}
		
	}

}
