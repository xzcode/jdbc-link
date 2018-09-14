package com.xzcode.jdbclink.core.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.xzcode.jdbclink.core.entity.model.EntityField;

/**
 * 列信息注解
 * 
 * 
 * @author zai
 * 2017-05-26
 */
@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface Column {
	
	/**
	 * 列名
	 * @return
	 * 
	 * @author zai
	 * 2017-05-26
	 */
    String name() default "";
    
    /**
     * 是否可为 null
     * @return
     * 
     * @author zai
     * 2017-05-26
     */
    boolean nullable() default true;

    /**
     * 最大长度
     * @return
     * 
     * @author zai
     * 2017-05-26
     */
    int length() default 255;
    
    /**
     * 精度
     * @return
     * 
     * @author zai
     * 2017-05-26
     */
    int precision() default 0;

}