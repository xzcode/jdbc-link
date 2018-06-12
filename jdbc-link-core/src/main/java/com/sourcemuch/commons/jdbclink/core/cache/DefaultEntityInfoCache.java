package com.sourcemuch.commons.jdbclink.core.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;
import com.sourcemuch.commons.jdbclink.core.annotations.Column;
import com.sourcemuch.commons.jdbclink.core.annotations.Id;
import com.sourcemuch.commons.jdbclink.core.annotations.Table;

public class DefaultEntityInfoCache implements IEntityInfoCache{
	
	private static final Map<Class<?>, EntityInfo> CACHE_MAP = new ConcurrentHashMap<>();

	@Override
	public EntityInfo getEntityInfo(Class<?> clazz) {
		if (CACHE_MAP.containsKey(clazz)) {
			return CACHE_MAP.get(clazz);
		}
		return null;
	}
	

	@Override
	public EntityInfo addEntityInfo(Class<?> clazz) {
		EntityInfo entityInfo = this.createEntityInfo(clazz);
		CACHE_MAP.put(clazz, entityInfo);
		return entityInfo;
	}

	@Override
	public EntityInfo createEntityInfo(Class<?> clazz) {
		
		EntityInfo entityInfo = new EntityInfo();
		entityInfo.setProps(new ArrayList<>());
		entityInfo.setColumns(new ArrayList<>());
		
		entityInfo.setClazz(clazz);
		
		Table table = clazz.getAnnotation(Table.class);
		Assert.notNull(table, "The table annotation must not be null");
		
		entityInfo.setTable(table.name());
		
		Assert.notNull(table.name(), "The table name must not be null");
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field fd : fields) {
			Column column = fd.getAnnotation(Column.class);
			if (column != null) {
				
				entityInfo.getColumns().add(column.name());
				entityInfo.getProps().add(fd.getName());
				
				Id id = fd.getAnnotation(Id.class);
				if(id != null){
					entityInfo.setId(column.name());
					entityInfo.setIdClass(fd.getDeclaringClass());
				}
			}
		}
		return entityInfo;
	}


	@Override
	public String getIdColumnName() {
		return "uid";
	}
	

}
