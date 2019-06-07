package com.xzcode.jdbclink.core.sql.update;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.xzcode.jdbclink.core.JdbcLinkConfig;
import com.xzcode.jdbclink.core.entity.EntityFieldInfo;
import com.xzcode.jdbclink.core.entity.EntityInfo;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.entity.model.EntityField;
import com.xzcode.jdbclink.core.models.SqlAndParams;
import com.xzcode.jdbclink.core.resolver.ISqlResolver;
import com.xzcode.jdbclink.core.sql.interfaces.ExecuteAble;
import com.xzcode.jdbclink.core.util.ShowSqlUtil;

public class Update implements ExecuteAble{
	
	protected JdbcLinkConfig config;
	
	protected EntityInfo entityInfo;
	
	protected UpdateSet set;
	
	protected ISqlResolver sqlResolver;
	
	protected String database;
	
	/*protected LimitParam limit;*/
	
	//private static final Class<?>[] NULL_CLASS_ARRAY = new Class<?>[]{};
	
	//private static final Object[] NULL_OBJECT_ARRAY = new Object[]{};
	
	public Update() {
	}
	
	public Update(Class<?> clazz, JdbcLinkConfig config) {
		this.config = config;
		this.entityInfo = config.getEntityInfoCache().getEntityInfo(clazz);
		this.sqlResolver = config.getSqlResolver();
	}
	
	
	public UpdateSet set() {
		return this.set = new UpdateSet(this);
	}
	
	
	public int update(IEntity entity){
		return updateNullable(entity);
	}
	
	public int updateNullable(IEntity entity, EntityField...nullableFields){
		int result = 0;
		
		try {
			EntityInfo entityInfo = config.getEntityInfoCache().getEntityInfo(entity.getClass());
			StringBuilder sql = config.getStringBuilderPool().get();
			//Class<?> clazz = entity.getClass();
			
			List<EntityFieldInfo> fieldInfos = entityInfo.getFieldInfos();
			List<Object> args = new ArrayList<>(fieldInfos.size());
			
			int fieldInfosSize = fieldInfos.size();
			
			sql.append(" update ");
			sql.append("`").append(entityInfo.getDatabase()).append("`").append(".");
			sql.append("`").append(entityInfo.getTable()).append("`");
			sql.append(" set ");
			Object uid = null;
			for (int i = 0; i < fieldInfosSize; i++) {
				
				Field field = fieldInfos.get(i).getField();
				//Method setMethod = clazz.getMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
				//Method getMethod = clazz.getMethod("get" + StringUtils.capitalize(field.getName()), NULL_CLASS_ARRAY);
				Object getObj = field.get(entity);
				if (getObj != null) {
					
					if (entityInfo.getPrimaryKeyFieldInfo().getPropName().equalsIgnoreCase(field.getName())) {
						uid = getObj;
					}else{
						args.add(getObj);
						sql.append("`").append(fieldInfos.get(i).getColumn()).append("`").append("=?,");
					}
				}else {
					if (nullableFields != null && nullableFields.length > 0) {
						for (EntityField nullCol : nullableFields) {
							if (fieldInfos.get(i).getColumn().equals(nullCol.fieldName())) {
								args.add(null);
								sql.append("`").append(fieldInfos.get(i).getColumn()).append("`").append("=?,");
							}
						}
					}
				}
			}
			
			sql.setLength(sql.length() - 1);
			
			sql.append(" where ")
			.append("`")
			.append(entityInfo.getPrimaryKeyFieldInfo().getColumn())
			.append("`")
			.append(" = ? ");
			args.add(uid);
			
			String sqlStr = sql.toString();
			
			config.getStringBuilderPool().returnOject(sql);
			
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
			
			result = this.config.getJdbcTemplate().update(statement);
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	@Override
	public int execute() {
		SqlAndParams sqlAndParams = sqlResolver.handelUpdate(this);
		ShowSqlUtil.debugSqlAndParams(sqlAndParams);
		return this.config.getJdbcTemplate().update(sqlAndParams.getSql(), sqlAndParams.getArgs().toArray());
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
	
	public ISqlResolver getSqlResolver() {
		return sqlResolver;
	}
	
	public void setSqlResolver(ISqlResolver sqlResolver) {
		this.sqlResolver = sqlResolver;
	}

	@Override
	public ExecuteAble getExecuteAble() {
		return this;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	/*public void setLimit(LimitParam limitParam) {
		this.limit = limitParam;
	}*/
}
