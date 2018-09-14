package com.xzcode.jdbclink.core.pool.string;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * 池化 StringBuilder 工厂
 * 
 * 
 * @author zai
 * 2018-09-08 16:08:25
 */
public class PooledStringBuilderFactory extends BasePooledObjectFactory<StringBuilder> {
	
	@Override
	public StringBuilder create() throws Exception {
		return new StringBuilder();
	}

	@Override
	public PooledObject<StringBuilder> wrap(StringBuilder sb) {
		return new DefaultPooledObject<StringBuilder>(sb);
	}
	
	
}
