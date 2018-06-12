package com.sourcemuch.commons.jdbclink.core.sql.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sourcemuch.commons.jdbclink.core.format.KeyFormatter;
import com.sourcemuch.commons.jdbclink.core.format.ValueFormatter;

public class ListMapRowMapper implements RowMapper<Map<String, Object>>{
	
	private KeyFormatter keyFormatter;
	private ValueFormatter valueFormatter;
	
	public ListMapRowMapper(KeyFormatter keyFormatter, ValueFormatter valueFormatter) {
		this.keyFormatter = keyFormatter;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Map<String, Object> map = new LinkedHashMap<>();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		for(int i = 1; i<= columnCount; i++) {
		    map.put(keyFormatter.format(metaData.getColumnLabel(i)),  valueFormatter.format(rs.getObject(i)));
	    }
		return map;
	}

}