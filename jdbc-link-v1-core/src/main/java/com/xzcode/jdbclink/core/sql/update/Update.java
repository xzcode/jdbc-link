package com.xzcode.jdbclink.core.sql.update;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.xzcode.jdbclink.core.EntityInfo;
import com.xzcode.jdbclink.core.cache.IEntityInfoCache;
import com.xzcode.jdbclink.core.models.SqlAndParams;
import com.xzcode.jdbclink.core.pool.string.StringBuilderPool;
import com.xzcode.jdbclink.core.sql.AbstractCommon;
import com.xzcode.jdbclink.core.sql.interfaces.ExecuteAble;
import com.xzcode.jdbclink.core.util.ShowSqlUtil;
import com.xzcode.jdbclink.core.util.SqlUtil;

public class Update extends AbstractCommon implements ExecuteAble{
	
	protected EntityInfo entityInfo;
	
	protected UpdateSet set;
	
	/*protected LimitParam limit;*/
	
	private static final Class<?>[] NULL_CLASS_ARRAY = new Class<?>[]{};
	
	private static final Object[] NULL_OBJECT_ARRAY = new Object[]{};
	
	public Update() {
	}
	
	public Update(Class<?> clazz, JdbcTemplate jdbcTemplate, StringBuilderPool stringBuilderPool, IEntityInfoCache entityInfoCache) {
		this.stringBuilderPool = stringBuilderPool;
		this.jdbcTemplate = jdbcTemplate;
		this.entityInfoCache = entityInfoCache;
		this.entityInfo = entityInfoCache.getEntityInfo(clazz);
	}
	
	
	public UpdateSet set() {
		return this.set = new UpdateSet(this);
	}
	
	
	public int update(Object record){
		return updateNullable(record);
	}
	
	public int updateNullable(Object record, String...nullableColumns){
		int result = 0;
		
		try {
			EntityInfo entityInfo = entityInfoCache.getEntityInfo(record.getClass());
			StringBuilder sql = stringBuilderPool.get();
			Class<?> clazz = record.getClass();
			
			List<Object> args = new ArrayList<>(entityInfo.getColumns().size());
			sql.append(" update ").append(entityInfo.getTable());
			sql.append(" set ");
			Object uid = null;
			for (int i = 0; i < entityInfo.getColumns().size(); i++) {
				Field field = clazz.getDeclaredField(entityInfo.getProps().get(i));
				//Method setMethod = clazz.getMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
				Method getMethod = clazz.getMethod("get" + StringUtils.capitalize(field.getName()), NULL_CLASS_ARRAY);
				Object getObj = getMethod.invoke(record, NULL_OBJECT_ARRAY);
				if (getObj != null) {
					
					if (entityInfo.getId().equalsIgnoreCase(field.getName())) {
						uid = getObj;
					}else{
						args.add(getObj);
						sql.append(entityInfo.getColumns().get(i)).append("=?,");
					}
				}else {
					if (nullableColumns != null && nullableColumns.length > 0) {
						for (String nullCol : nullableColumns) {
							if (entityInfo.getColumns().get(i).equals(nullCol)) {
								args.add(null);
								sql.append(entityInfo.getColumns().get(i)).append("=?,");
							}
						}
					}
				}
			}
			
			sql.setLength(sql.length() - 1);
			
			sql.append(" where ")
			.append(entityInfo.getId())
			.append(" = ? ");
			args.add(uid);
			
			String sqlStr = sql.toString();
			
			stringBuilderPool.returnOject(sql);
			
			ShowSqlUtil.debugSqlAndParams(sqlStr, args);
			
			PreparedStatementCreator statement = new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement prepareStatement = con.prepareStatement(sqlStr, Statement.RETURN_GENERATED_KEYS);
					for (int i = 1; i <= args.size(); i++) {
						prepareStatement.setObject(i, args.get(i - 1));
					}
					return prepareStatement;
				}
			};
			
			/*PreparedStatementCallback<int[]> statementCallback = new PreparedStatementCallback<int[]>() {

				@Override
				public int[] doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					for (int i = 1; i <= args.size(); i++) {
						ps.setObject(i, args.get(i - 1));
					}
					return ps.executeBatch();
				}
				
			};*/
			
			result = this.jdbcTemplate.update(statement);
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	@Override
	public int execute() {
		SqlAndParams sqlAndParams = SqlUtil.handelUpdate(this, stringBuilderPool);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		return this.jdbcTemplate.update(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray());
	}
	
	//------------------------
	
	protected void setEntityInfo(EntityInfo entityInfo) {
		this.entityInfo = entityInfo;
	}
	
	public EntityInfo getEntityInfo() {
		return entityInfo;
	}
	
	public UpdateSet getSet() {
		return set;
	}

	@Override
	public ExecuteAble getExecuteAble() {
		return this;
	}

	
	/*public void setLimit(LimitParam limitParam) {
		this.limit = limitParam;
	}*/
}
