package com.sourcemuch.commons.jdbclink.core.sql.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;

public class EntityResultSetExtractor<E> implements ResultSetExtractor<E>{
	
	private Class<E> clazz;
	private EntityInfo entityInfo;
	
	public EntityResultSetExtractor(Class<E> clazz, EntityInfo entityInfo) {
		this.clazz = clazz;
		this.entityInfo  =  entityInfo;
	}

	@Override
	public E extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		E instance = null;
		try {
				if (rs.next()) {
					instance = clazz.newInstance();
					for (int i = 0; i < entityInfo.getProps().size(); i++) {
						Object value = rs.getObject(entityInfo.getProps().get(i));
						Field field = instance.getClass().getDeclaredField(entityInfo.getProps().get(i));
						field.setAccessible(true);
						if (field != null) {
							field.set(instance, value);
						}
					}
				}
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return instance;
	}

}
