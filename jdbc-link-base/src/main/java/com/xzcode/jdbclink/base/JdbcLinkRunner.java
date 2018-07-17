package com.xzcode.jdbclink.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.xzcode.jdbclink.base.handler.BatchInsertResultSetHandler;
import com.xzcode.jdbclink.base.handler.InsertResultSetHandler;
import com.xzcode.jdbclink.base.handler.QueryResultSetHandler;
import com.xzcode.jdbclink.base.model.BatchParamInfo;
import com.xzcode.jdbclink.base.model.ParamInfo;
import com.xzcode.jdbclink.base.util.ShowSqlUtil;

public class JdbcLinkRunner {
	
	protected boolean supportsBatchUpdates = true;
	
	protected DataSource dataSource;
	
	
	public JdbcLinkRunner(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		
		
		Connection conn = dataSource.getConnection();
		
    	//判断数据库是否支持批量BatchUpdate
    	supportsBatchUpdates = conn.getMetaData().supportsBatchUpdates();
    	
    	close(conn);
	}
	
	
	public void  close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }
    
    public void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }
    
    public DataSource getDataSource() {
		return dataSource;
	}
    
    protected Connection getConnection() throws SQLException {
        if (this.getDataSource() == null) {
            throw new SQLException(
                    "QueryRunner requires a DataSource to be "
                            + "invoked in this way, or a Connection should be passed in");
        }
        return this.getDataSource().getConnection();
    }
    
    /**
     * 参数填充
     * @param stmt
     * @param paramInfos
     * @throws SQLException
     * 
     * @author zai
     * 2018-07-17 17:18:40
     */
    public void fillStatement(PreparedStatement stmt, List<ParamInfo> paramInfos)
            throws SQLException {

        if (paramInfos == null) {
            return;
        }
        
        ParamInfo paramInfo = null;
        
        for (int i = 0; i < paramInfos.size(); i++) {
        	paramInfo = paramInfos.get(i);
        	if (paramInfo.getSqlType() != null) {
        		stmt.setObject(i + 1, paramInfo.getParam(), paramInfo.getSqlType());
        		continue;
			}
        	stmt.setObject(i + 1, paramInfo.getParam());
        }
    }


	
	private void checkCommonParams(Connection conn, boolean closeConn, String sql) throws SQLException {
		
		if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }

	}
	
	/**
	 * 检查批量更新支持
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-17 17:01:09
	 */
	private void checkSupportsBatchUpdates() throws SQLException {
		
		if (!supportsBatchUpdates) {
            throw new SQLException("This database is not support for batch updates!");
        }
	}
	
	/**
	 * 数据插入操作
	 * @param conn
	 * @param sql
	 * @param paramInfos
	 * @param insertResultSetHandler
	 * @param generatedColumnNames 自动生成主键的列名
	 * @param closeConn
	 * @return
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-17 17:01:26
	 */
	public <T> T insert(Connection conn, String sql, List<ParamInfo> paramInfos, InsertResultSetHandler<T> insertResultSetHandler, String[] generatedColumnNames, boolean closeConn)
            throws SQLException {
		
		checkCommonParams(conn, closeConn, sql);
		
		
		PreparedStatement stmt = null;
        try {
        	
        	ShowSqlUtil.debugSqlAndParamInfos(sql, paramInfos);
            
            if (generatedColumnNames != null) {
            	stmt = conn.prepareStatement(sql, generatedColumnNames);
            	this.fillStatement(stmt, paramInfos);
                int updateRows = stmt.executeUpdate();
            	ResultSet genKeysRs = stmt.getGeneratedKeys();
            	if (insertResultSetHandler != null) {
            		return insertResultSetHandler.handle(updateRows, genKeysRs);					
				}
			}else {
				stmt = conn.prepareStatement(sql);
				this.fillStatement(stmt, paramInfos);
				int updateRows = stmt.executeUpdate();
				if (insertResultSetHandler != null) {
					return insertResultSetHandler.handle(updateRows, null);					
				}
			}
        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }
        
        return null;

    }
	
	public <T> T insertBatch(Connection conn, String sql, List<BatchParamInfo> batchParamInfos, BatchInsertResultSetHandler<T> batchInsertResultSetHandler, String[] generatedColumnNames, boolean closeConn)
            throws SQLException {
		checkSupportsBatchUpdates();
		checkCommonParams(conn, closeConn, sql);
		
		PreparedStatement stmt = null;
        try {
        	
        	ShowSqlUtil.debugSqlAndBatchParamInfos(sql, batchParamInfos);
            
            if (generatedColumnNames != null) {
            	stmt = conn.prepareStatement(sql, generatedColumnNames);
            	
            	for (BatchParamInfo batchParamInfo : batchParamInfos) {
            		this.fillStatement(stmt, batchParamInfo.getParamInfos());
            		stmt.addBatch();
				}
                int[] updateRows = stmt.executeBatch();
            	ResultSet genKeysRs = stmt.getGeneratedKeys();
            	return batchInsertResultSetHandler.handle(updateRows, genKeysRs);
			}else {
				stmt = conn.prepareStatement(sql);
				for (BatchParamInfo batchParamInfo : batchParamInfos) {
            		this.fillStatement(stmt, batchParamInfo.getParamInfos());
            		stmt.addBatch();
				}
				int[] updateRows = stmt.executeBatch();
				return batchInsertResultSetHandler.handle(updateRows, null);
			}
        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }
    }
	
	/**
	 * 更新
	 * @param conn
	 * @param sql
	 * @param paramInfos
	 * @param closeConn
	 * @return
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-17 17:13:10
	 */
	public int update(Connection conn, String sql, List<ParamInfo> paramInfos, boolean closeConn)
            throws SQLException {
        
		checkCommonParams(conn, closeConn, sql);
		
		PreparedStatement stmt = null;
        try {
        	
        	ShowSqlUtil.debugSqlAndParamInfos(sql, paramInfos);
			stmt = conn.prepareStatement(sql);
			this.fillStatement(stmt, paramInfos);
			
			return stmt.executeUpdate();
        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }

    }
	
	/**
	 * 批量更新
	 * @param conn
	 * @param sql
	 * @param batchParamInfos
	 * @param closeConn
	 * @return
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-17 17:13:01
	 */
	public int[] updateBatch(Connection conn, String sql, List<BatchParamInfo> batchParamInfos, boolean closeConn) throws SQLException {
		
		checkSupportsBatchUpdates();
		checkCommonParams(conn, closeConn, sql);
		
		PreparedStatement stmt = null;
        try {
        	
        	ShowSqlUtil.debugSqlAndBatchParamInfos(sql, batchParamInfos);
            
        	stmt = conn.prepareStatement(sql);
        	
        	for (BatchParamInfo batchParamInfo : batchParamInfos) {
        		this.fillStatement(stmt, batchParamInfo.getParamInfos());
        		stmt.addBatch();
			}
        	
        	return stmt.executeBatch();
        	
        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }
    }
	
	/**
	 * 删除
	 * @param conn
	 * @param sql
	 * @param paramInfos
	 * @param closeConn
	 * @return
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-17 17:13:42
	 */
	public int delete(Connection conn, String sql, List<ParamInfo> paramInfos, boolean closeConn)
            throws SQLException {
        
		return update(conn, sql, paramInfos, closeConn);

    }
	
	/**
	 * 批量删除
	 * @param conn
	 * @param sql
	 * @param batchParamInfos
	 * @param closeConn
	 * @return
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-17 17:13:47
	 */
	public int[] deleteBatch(Connection conn, String sql, List<BatchParamInfo> batchParamInfos, boolean closeConn)
            throws SQLException {
        
		return updateBatch(conn, sql, batchParamInfos, closeConn);

    }
	
	
	public <T> T query(Connection conn, String sql, List<ParamInfo> paramInfos, QueryResultSetHandler<T> resultSetHandler, boolean closeConn) throws SQLException {
		
		checkCommonParams(conn, closeConn, sql);
		PreparedStatement stmt = null;
        try {
        	
        	ShowSqlUtil.debugSqlAndParamInfos(sql, paramInfos);
            
        	stmt = conn.prepareStatement(sql);
        	
    		this.fillStatement(stmt, paramInfos);
    		
        	return resultSetHandler.handle(stmt.executeQuery());
        	
        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }
    }
	

}
