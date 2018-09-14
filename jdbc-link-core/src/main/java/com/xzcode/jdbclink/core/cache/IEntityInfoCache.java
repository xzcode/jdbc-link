package com.xzcode.jdbclink.core.cache;

import com.xzcode.jdbclink.core.entity.EntityInfo;

public interface IEntityInfoCache {

	EntityInfo getEntityInfo(Class<?> clazz);


	EntityInfo addEntityInfo(Class<?> clazz);


	EntityInfo createEntityInfo(Class<?> clazz);




}
