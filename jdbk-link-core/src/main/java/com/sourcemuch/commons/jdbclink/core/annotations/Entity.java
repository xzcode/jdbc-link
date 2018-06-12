package com.sourcemuch.commons.jdbclink.core.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * 实体类注解
 * 
 * 
 * @author zai
 * 2017-05-26
 */
@Target(TYPE) 
@Retention(RUNTIME)
public @interface Entity {
	
}

