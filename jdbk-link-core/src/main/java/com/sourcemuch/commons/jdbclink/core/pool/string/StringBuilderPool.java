package com.sourcemuch.commons.jdbclink.core.pool.string;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class StringBuilderPool {
	
	private GenericObjectPool<StringBuilder> pool;
	
	public StringBuilderPool() {
		init();
	}
    
	
	public void init() {
		 GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		 
		//池对象耗尽之后是否阻塞,maxWait<0时一直等待
		 poolConfig.setBlockWhenExhausted(false);
		 poolConfig.setMaxWaitMillis(0);
         poolConfig.setMinIdle(10);
         poolConfig.setMaxIdle(10);
         poolConfig.setMaxTotal(-1);
         poolConfig.setTestOnBorrow(false);
         poolConfig.setTestOnReturn(false);
         poolConfig.setTestOnCreate(false);
         poolConfig.setTestWhileIdle(false);
         poolConfig.setLifo(false);
         poolConfig.setNumTestsPerEvictionRun(1000 * 60);
         // 初始化对象池
         pool = new GenericObjectPool<StringBuilder>(new PooledStringBuilderFactory() ,poolConfig);
		 
         AbandonedConfig abandonedConfig = new AbandonedConfig();
         
         abandonedConfig.setRemoveAbandonedOnMaintenance(true); //在Maintenance的时候检查是否有泄漏

         abandonedConfig.setRemoveAbandonedOnBorrow(true); //borrow 的时候检查泄漏

         abandonedConfig.setRemoveAbandonedTimeout(20); //如果一个对象borrow之后20秒还没有返还给pool，认为是泄漏的对象

         pool.setAbandonedConfig(abandonedConfig);
         
         pool.setTimeBetweenEvictionRunsMillis(60000); //60秒运行一次维护任务
         
         
		 
	}
	
	public void returnOject(StringBuilder sb) {
		sb.setLength(0);
		pool.returnObject(sb);
	}
	
	public StringBuilder get() {
		try {
			return pool.borrowObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
