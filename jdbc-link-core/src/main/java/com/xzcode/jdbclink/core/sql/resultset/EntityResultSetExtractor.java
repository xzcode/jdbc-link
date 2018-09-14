package com.xzcode.jdbclink.core.sql.resultset;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.EntityInfo;

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
					List<EntityFieldInfo> fieldInfos = entityInfo.getFieldInfos();
					int fieldInfosSize = fieldInfos.size();
					for (int i = 0; i < fieldInfosSize; i++) {
						Object value = rs.getObject(fieldInfos.get(i).getPropName());
						Field field = fieldInfos.get(i).getField();
						field.set(instance, value);
					}
				}
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return instance;
	}

}
