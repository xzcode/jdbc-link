package com.xzcode.jdbclink.core.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * 表信息注解，用于表实体类
 * 
 * 
 * @author zai
 * 2017-05-26
 */
@Target(TYPE) 
@Retention(RUNTIME)
public @interface Table {
	
	/**
	 * 表名
	 * @return
	 * 
	 * @author zai
	 * 2017-05-26
	 */
    String name() default "";
    
    /**
     * 别名
     * @return
     * 
     * @author zai
     * 2017-05-26
     */
    String alias() default "";

    /**
     * 联查时候使用的前缀
     * @return
     * 
     * @author zai
     * 2017-05-26
     */
    String prefix() default "";
}

