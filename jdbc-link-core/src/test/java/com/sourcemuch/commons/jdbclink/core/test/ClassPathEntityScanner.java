package com.sourcemuch.commons.jdbclink.core.test;

import com.sourcemuch.commons.jdbclink.core.annotations.Entity;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class ClassPathEntityScanner {
	
	public static void main(String[] args) {
		new FastClasspathScanner("com.sourcemuch.common.jdbclink.test")  
	    .matchClassesWithAnnotation(Entity.class, c -> {
	    	try {
				System.out.println(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    })
	    .scan();
	}
	
}
