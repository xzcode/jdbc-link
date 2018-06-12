package com.sourcemuch.commons.jdbclink.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.sourcemuch.commons.jdbclink.core.JdbcLink;
import com.sourcemuch.commons.jdbclink.core.JdbcLinkConfig;

@Configuration
@ConfigurationProperties(prefix="sourcemuch.commons.jdbclink")
@ConditionalOnProperty(prefix="sourcemuch.commons.jdbclink", name="enabled", havingValue="true")
public class JdbcLinkAutoConfig{
	
	private Boolean enabled = false;
	
	private String[] scanPackage;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Bean
	@ConditionalOnMissingBean(JdbcLink.class)
	//@ConditionalOnBean(JdbcTemplate.class)
	public JdbcLink jdbcLink() {
		Assert.notNull(jdbcTemplate, " Can't autowrite spring jdbcTemplate! ");
		Assert.notNull(scanPackage, " Jdbclink: property 'scanPackage' can not be null! ");
		
		JdbcLinkConfig config = new JdbcLinkConfig(jdbcTemplate, scanPackage);
		JdbcLink jdbcLink = new JdbcLink(config);
		
		return jdbcLink;
	}

	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String[] getScanPackage() {
		return scanPackage;
	}
	
	public void setScanPackage(String...scanPackage) {
		this.scanPackage = scanPackage;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}