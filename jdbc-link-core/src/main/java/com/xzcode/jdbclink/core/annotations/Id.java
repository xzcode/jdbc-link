package com.xzcode.jdbclink.core.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 唯一主键标识
 * 
 * 
 * @author zai
 * 2017-05-26
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Id {

}
