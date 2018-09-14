package com.xzcode.jdbclink.core.sql.resultset;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.EntityInfo;

public class EntityRowMapper<E> implements RowMapper<E>{
	
	private Class<E> clazz;
	private EntityInfo entityInfo;
	
	public EntityRowMapper(Class<E> clazz, EntityInfo entityInfo) {
		this.clazz = clazz;
		this.entityInfo = entityInfo;
	}

		@Override
		public E mapRow(ResultSet rs, int rowNum) throws SQLException {
			E instance = null;
			try {
					instance = clazz.newInstance();
					List<EntityFieldInfo> fieldInfos = entityInfo.getFieldInfos();
					int fieldInfosSize = fieldInfos.size();
					for (int i = 0; i < fieldInfosSize; i++) {
						Object value = rs.getObject(fieldInfos.get(i).getPropName());
						Field field = fieldInfos.get(i).getField();
						field.set(instance, value);
					}
			} catch (Exception e) {
				if (e instanceof EmptyResultDataAccessException) {
					return null;
				}
				throw new SQLException(e);
			}
			return instance;
		}

}