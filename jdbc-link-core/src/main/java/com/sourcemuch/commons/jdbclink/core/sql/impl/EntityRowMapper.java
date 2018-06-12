package com.sourcemuch.commons.jdbclink.core.sql.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;

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
					for (int i = 0; i < entityInfo.getProps().size(); i++) {
						Object value = rs.getObject(entityInfo.getProps().get(i));
						Field field = instance.getClass().getDeclaredField(entityInfo.getProps().get(i));
						field.setAccessible(true);
						if (field != null) {
							field.set(instance, value);
						}
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