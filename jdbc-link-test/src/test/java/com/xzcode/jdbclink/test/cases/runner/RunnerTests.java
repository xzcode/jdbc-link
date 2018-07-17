package com.xzcode.jdbclink.test.cases.runner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.impl.SimpleLogger;

import com.xzcode.jdbclink.base.JdbcLinkRunner;
import com.xzcode.jdbclink.base.handler.BatchInsertResultSetHandler;
import com.xzcode.jdbclink.base.handler.InsertResultSetHandler;
import com.xzcode.jdbclink.base.handler.impl.ArrayQueryResultSetHandler;
import com.xzcode.jdbclink.base.handler.impl.IntQueryResultSetHandler;
import com.xzcode.jdbclink.base.handler.impl.ListMapQueryResultSetHandler;
import com.xzcode.jdbclink.base.model.BatchParamInfo;
import com.xzcode.jdbclink.base.model.ParamInfo;
import com.xzcode.jdbclink.test.DataSourceFactory;

public class RunnerTests {
	
	private DataSource dataSource;
	
	private JdbcLinkRunner runner;
	
	@Before
	public void init() throws SQLException {
		
		System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
		
		dataSource = DataSourceFactory.getDataSource();
		runner = new JdbcLinkRunner(dataSource);
	}
	
	/**
	 * 单项插入测试
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-15 13:03:47
	 */
	@Test
	public void singleInsert() throws SQLException {
		
		List<ParamInfo> paramInfos = new ArrayList<>();
		
		paramInfos.add(ParamInfo.create("dick1", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("杭州", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("100000", Types.VARCHAR));
		paramInfos.add(ParamInfo.create(999, Types.INTEGER));
		paramInfos.add(ParamInfo.create(null, Types.OTHER));
		
		String[] genCols = new String[]{"uid"};
		
		String result = runner.insert(
				dataSource.getConnection(), 
				"INSERT INTO shose_info (name, city, price, number, picture) VALUES (?,?,?,?,?)",
				paramInfos , 
				new InsertResultSetHandler<String>() {

					@Override
					public String handle(int updateRows, ResultSet genKeyRs) throws SQLException {
						String result = "";
						
						result += "updateRows:" + updateRows + "\n";
						
						if (genKeyRs == null) {
							return result;
						}
						
						result += "genKeys:";
						System.out.println("genKeyCount:" + genKeyRs.getMetaData().getColumnCount());
						while(genKeyRs.next()) {
								result += genKeyRs.getObject(1);
								result += ",";
						}
						
						return result;
					}
					
				}, 
				genCols, 
				true
				);
		
		System.out.println(result);
	}
	
	/**
	 * 批量插入测试
	 * @throws SQLException
	 * 
	 * @author zai
	 * 2018-07-15 13:07:42
	 */
	@Test
	public void batchInsert() throws SQLException {
		
		List<BatchParamInfo> batchParamInfos = new ArrayList<>();
		
		List<ParamInfo> paramInfos = new ArrayList<>();
		
		paramInfos.add(ParamInfo.create("dick1", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("杭州", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("100000", Types.VARCHAR));
		paramInfos.add(ParamInfo.create(999, Types.INTEGER));
		paramInfos.add(ParamInfo.create(null, Types.OTHER));
		
		batchParamInfos.add(BatchParamInfo.create(paramInfos));
		
		List<ParamInfo> paramInfos2 = new ArrayList<>();
		paramInfos2.add(ParamInfo.create("dick2", Types.VARCHAR));
		paramInfos2.add(ParamInfo.create("杭州2", Types.VARCHAR));
		paramInfos2.add(ParamInfo.create("100002", Types.VARCHAR));
		paramInfos2.add(ParamInfo.create(992, Types.INTEGER));
		paramInfos2.add(ParamInfo.create(null, Types.OTHER));
		
		batchParamInfos.add(BatchParamInfo.create(paramInfos2));
		
		String[] genCols = new String[]{"uid"};
		
		String result = runner.insertBatch(
				dataSource.getConnection(), 
				"INSERT INTO shose_info (name, city, price, number, picture) VALUES (?,?,?,?,?)",
				batchParamInfos , 
				new BatchInsertResultSetHandler<String>() {

					@Override
					public String handle(int[] updateRows, ResultSet genKeyRs) throws SQLException {
						String result = "";
						
						result += "updateRows:" + Arrays.asList(updateRows) + "\n";
						
						if (genKeyRs == null) {
							return result;
						}
						
						result += "genKeys:";
						System.out.println("genKeyCount:" + genKeyRs.getMetaData().getColumnCount());
						while(genKeyRs.next()) {
								result += genKeyRs.getObject(1);
								result += ",";
						}
						
						return result;
					}
					
				}, 
				genCols, 
				true
				);
		
		System.out.println(result);
	}
	
	@Test
	public void delete() throws SQLException {
		List<ParamInfo> paramInfos = new ArrayList<>();
		paramInfos.add(ParamInfo.create("dick2", Types.VARCHAR));
		
		int delete = runner.delete(dataSource.getConnection(), "DELETE FROM shose_info WHERE NAME = ?", paramInfos, true);
		
		System.out.println(delete);
	}
	
	@Test
	public void update() throws SQLException {
		List<ParamInfo> paramInfos = new ArrayList<>();
		paramInfos.add(ParamInfo.create("dick4", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("dick2", Types.VARCHAR));
		
		int delete = runner.delete(dataSource.getConnection(), "update shose_info set name = ? WHERE NAME = ?", paramInfos, true);
		
		System.out.println(delete);
	}
	
	
	@Test
	public void batchUpdate() throws SQLException {
		
		List<BatchParamInfo> batchParamInfos = new ArrayList<>();
		
		List<ParamInfo> paramInfos = new ArrayList<>();
		
		paramInfos.add(ParamInfo.create("dick1", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("dick5", Types.VARCHAR));
		
		batchParamInfos.add(BatchParamInfo.create(paramInfos));
		
		List<ParamInfo> paramInfos2 = new ArrayList<>();
		paramInfos2.add(ParamInfo.create("dick3", Types.VARCHAR));
		paramInfos2.add(ParamInfo.create("dick6", Types.VARCHAR));
		
		batchParamInfos.add(BatchParamInfo.create(paramInfos2));
		
		
		int[] updateBatch = runner.updateBatch(
				dataSource.getConnection(), 
				"update shose_info set name = ?  WHERE NAME = ?",
				batchParamInfos , 
				true
				);
		System.out.println(StringUtils.join(updateBatch, ','));
	}
	
	
	@Test
	public void queryAllListMap() throws SQLException {
		/*List<ParamInfo> paramInfos = new ArrayList<>();
		paramInfos.add(ParamInfo.create("dick4", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("dick2", Types.VARCHAR));*/
		
		List<Map<String,Object>> list = runner.query(dataSource.getConnection(), "select * from shose_info", null, new ListMapQueryResultSetHandler(), true);
		
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		
	}
	
	@Test
	public void queryInt() throws SQLException {
		/*List<ParamInfo> paramInfos = new ArrayList<>();
		paramInfos.add(ParamInfo.create("dick4", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("dick2", Types.VARCHAR));*/
		
		int count = runner.query(dataSource.getConnection(), "select count(*) from shose_info", null, new IntQueryResultSetHandler(), true);
		
		System.out.println(count);
	}
	
	@Test
	public void queryArray() throws SQLException {
		/*List<ParamInfo> paramInfos = new ArrayList<>();
		paramInfos.add(ParamInfo.create("dick4", Types.VARCHAR));
		paramInfos.add(ParamInfo.create("dick2", Types.VARCHAR));*/
		
		Object[] query = runner.query(dataSource.getConnection(), "select * from shose_info", null, new ArrayQueryResultSetHandler(), true);
		
		System.out.println(Arrays.asList(query));
	}

	@Test
	public void typeCast() throws SQLException {
		int i = (int) 1998999D;
		short e = (short) i;
		System.out.println(i + "==" + e + " : " + (i == e));
	}
	
}
