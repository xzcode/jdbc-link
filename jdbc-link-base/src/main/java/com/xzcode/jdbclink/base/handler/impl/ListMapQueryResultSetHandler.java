package com.xzcode.jdbclink.base.handler.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xzcode.jdbclink.base.format.KeyFormatter;
import com.xzcode.jdbclink.base.format.ValueFormatter;
import com.xzcode.jdbclink.base.format.impl.DefaultKeyFormatter;
import com.xzcode.jdbclink.base.format.impl.DefaultValueFormatter;
import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;

public class ListMapQueryResultSetHandler implements QueryResultSetHandler<List<Map<String, Object>>> {
	
	private KeyFormatter keyFormatter = new DefaultKeyFormatter();
	
	private ValueFormatter valueFormatter = new DefaultValueFormatter();
	
	

	@Override
	public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
		
		boolean last = rs.last();
		//no rows
		if (!last) {
			return null;
		}
		List<Map<String, Object>> list = new ArrayList<>(rs.getRow());
		rs.beforeFirst();
		Map<String, Object> map = null;
		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		while (rs.next()) {
			map = new LinkedHashMap<>();
			for(int i = 1; i<= columnCount; i++) {
			    map.put(getKeyFormatter().format(resultSetMetaData.getColumnLabel(i)), getValueFormatter().format(rs.getObject(i)));
		    }
		    list.add(map);
		}
		
		return list;
	}
	
	
	public KeyFormatter getKeyFormatter() {
		return keyFormatter;
	}
	
	public void setKeyFormatter(KeyFormatter keyFormatter) {
		this.keyFormatter = keyFormatter;
	}
	
	public ValueFormatter getValueFormatter() {
		return valueFormatter;
	}
	
	public void setValueFormatter(ValueFormatter valueFormatter) {
		this.valueFormatter = valueFormatter;
	}

}
