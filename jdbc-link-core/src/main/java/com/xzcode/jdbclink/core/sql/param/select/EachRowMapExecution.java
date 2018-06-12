package com.xzcode.jdbclink.core.sql.param.select;

import java.util.Map;

/**
 * 对list map结果集执行每次遍历操作接口
 * 
 * 
 * @author zai
 * 2018-02-05
 */
public interface EachRowMapExecution {
	
	void exec(Map<String, Object> outterRowParams);

}
