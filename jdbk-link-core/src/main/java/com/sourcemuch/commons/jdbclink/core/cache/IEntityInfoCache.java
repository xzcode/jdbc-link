package com.sourcemuch.commons.jdbclink.core.cache;

import com.sourcemuch.commons.jdbclink.core.EntityInfo;

public interface IEntityInfoCache {

	EntityInfo getEntityInfo(Class<?> clazz);


	EntityInfo addEntityInfo(Class<?> clazz);


	EntityInfo createEntityInfo(Class<?> clazz);


	String getIdColumnName();
	
	


}
