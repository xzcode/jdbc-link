package com.xzcode.jdbclink.core.pool.string;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

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
