package com.xzcode.jdbclink.core.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xzcode.jdbclink.core.annotations.Column;
import com.xzcode.jdbclink.core.annotations.Id;
import com.xzcode.jdbclink.core.annotations.Table;
import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.exception.JdbcLinkRuntimeException;

/**
 * 实体信息缓存
 * 
 * 
 * @author zai
 * 2018-09-08 16:04:20
 */
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
		entityInfo.setFieldInfos(new ArrayList<>());
		
		entityInfo.setClazz(clazz);
		
		Table table = clazz.getAnnotation(Table.class);
		if (table == null) {
			throw new JdbcLinkRuntimeException("The table annotation must not be null");
		}
		if (table.name() == null) {
			throw new JdbcLinkRuntimeException("The table name must not be null");
		}
		entityInfo.setTable(table.name());
		
		entityInfo.setAlias(table.name());
		
		List<EntityFieldInfo> fieldInfos = entityInfo.getFieldInfos();
		EntityFieldInfo fieldInfo = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field fd : fields) {
			Column column = fd.getAnnotation(Column.class);
			if (column != null) {
				fd.setAccessible(true);
				fieldInfo = new EntityFieldInfo();
				fieldInfo.setColumn(column.name());
				fieldInfo.setPropName(fd.getName());
				fieldInfo.setField(fd);
				fieldInfo.setLength(column.length());
				fieldInfo.setNullable(column.nullable());
				
				Id id = fd.getAnnotation(Id.class);
				if(id != null){
					entityInfo.setPrimaryKeyFieldInfo(fieldInfo);
				}
				fieldInfos.add(fieldInfo);
			}
		}
		return entityInfo;
	}


	

}
