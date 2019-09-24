package com.xzcode.jdbclink.codegen.data.adapter.mysql;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.codegen.config.JdbcLinkEntityGeneratorConfig;
import com.xzcode.jdbclink.codegen.custom.clazz.IEntityClassNameGenerator;
import com.xzcode.jdbclink.codegen.custom.comment.column.ColumnComment;
import com.xzcode.jdbclink.codegen.custom.comment.column.IColumnCommentParser;
import com.xzcode.jdbclink.codegen.custom.datatype.IDataTypeConverter;
import com.xzcode.jdbclink.codegen.custom.module.IModuleNameGenerator;
import com.xzcode.jdbclink.codegen.custom.property.IEntityClassPropertyGenerator;
import com.xzcode.jdbclink.codegen.data.adapter.IDatabaseInfoAdapter;
import com.xzcode.jdbclink.codegen.model.ColumnInfo;
import com.xzcode.jdbclink.codegen.model.TableEntityInfo;

/**
 * mysql数据库信息适配
 * 
 * 
 * @author zai
 * 2018-04-14
 */
public class MysqlDatabaseInfoAdapter implements IDatabaseInfoAdapter{
	
	private static final Logger looger = LoggerFactory.getLogger(MysqlDatabaseInfoAdapter.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcLink(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private JdbcLinkEntityGeneratorConfig entityGeneratorConfig;
	public void setEntityGeneratorConfig(JdbcLinkEntityGeneratorConfig entityGeneratorConfig) {
		this.entityGeneratorConfig = entityGeneratorConfig;
	}
	
	private IEntityClassPropertyGenerator propertyGenerator;;
	
	public void setPropertyGenerator(IEntityClassPropertyGenerator propertyGenerator) {
		this.propertyGenerator = propertyGenerator;
	}
	
	private IDataTypeConverter javaTypeConverter;
	public void setJavaTypeConverter(IDataTypeConverter javaTypeConverter) {
		this.javaTypeConverter = javaTypeConverter;
	}
	
	private IColumnCommentParser columnCommentParser;
	public void setColumnCommentParser(IColumnCommentParser columnCommentParser) {
		this.columnCommentParser = columnCommentParser;
	}
	
	private IEntityClassNameGenerator classNameGenerator;


	public void setClassNameGenerator(IEntityClassNameGenerator classNameGenerator) {
		this.classNameGenerator = classNameGenerator;
	}
	
	private IModuleNameGenerator moduleNameGenerator;
	public void setModulePackageGenerator(IModuleNameGenerator moduleNameGenerator) {
		this.moduleNameGenerator = moduleNameGenerator;
	}

	



	

	








	public MysqlDatabaseInfoAdapter(JdbcTemplate jdbcTemplate, JdbcLinkEntityGeneratorConfig entityGeneratorConfig,
			IEntityClassPropertyGenerator propertyGenerator, IDataTypeConverter javaTypeConverter,
			IColumnCommentParser columnCommentParser, IEntityClassNameGenerator classNameGenerator,
			IModuleNameGenerator moduleNameGenerator) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.entityGeneratorConfig = entityGeneratorConfig;
		this.propertyGenerator = propertyGenerator;
		this.javaTypeConverter = javaTypeConverter;
		this.columnCommentParser = columnCommentParser;
		this.classNameGenerator = classNameGenerator;
		this.moduleNameGenerator = moduleNameGenerator;
	}
















	@Override
	public List<String> getDatabases() {
		String sql = "SHOW DATABASES";
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		
		List<String> databases = list.stream().map(map -> {
			return (String)map.get("Database");
		}).collect(Collectors.toList());
		
		return databases;
	}

	@Override
	public List<TableEntityInfo> getTableInfos(String databaseName) {
		String sql = "SHOW TABLE STATUS FROM `" + databaseName +"`";
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		
		List<TableEntityInfo> tableEntityInfos = list.stream().map(map -> {
			
			TableEntityInfo tableEntityInfo = new TableEntityInfo();
			tableEntityInfo.setTableName((String) map.get("Name"));
			tableEntityInfo.setTableComment((String) map.get("Comment"));
			tableEntityInfo.setColumns(getColumnsInfo(databaseName, tableEntityInfo.getTableName()));
			/*tableEntityInfo.setDatabaseName(databaseName);*/
			tableEntityInfo.setEntityClassName(classNameGenerator.generateClassName(tableEntityInfo.getTableName()));
			if (entityGeneratorConfig.isCreateModulePackage()) {
				String moduleName = moduleNameGenerator.generateModulePackageName(tableEntityInfo.getTableName(), null, null);
				tableEntityInfo.setFullPackageName(entityGeneratorConfig.getEntityBasicPackage() + "." + moduleName);
				tableEntityInfo.setModuleName(moduleName);
			}else {
				tableEntityInfo.setFullPackageName(entityGeneratorConfig.getEntityBasicPackage());
			}
			
			tableEntityInfo.setFullPackageClassName(tableEntityInfo.getFullPackageName() + "." + tableEntityInfo.getEntityClassName());
			
			List<ColumnInfo> columns = tableEntityInfo.getColumns();
			Set<String> importJavaTypes = new HashSet<>();
			
			for (ColumnInfo col : columns) {
				col.getFullPackageJavaType();
				importJavaTypes.add(col.getFullPackageJavaType());
			}
			tableEntityInfo.setImportJavaTypes(importJavaTypes);
			
			
			return tableEntityInfo;
		}).collect(Collectors.toList());
		
		return tableEntityInfos;
	}

	@Override
	public List<ColumnInfo> getColumnsInfo(String databaseName, String tableName) {
		
		if (looger.isDebugEnabled()) {
			looger.info("Getting column info from {}.{} .", databaseName, tableName);
		}
		
		String sql = "SHOW FULL COLUMNS FROM `" + databaseName + "`.`" + tableName +"`";
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		
		List<ColumnInfo> columnInfos = list.stream().map(map -> {
			
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setName((String) map.get("Field"));
			columnInfo.setDefaultValue((String) map.get("Default"));
			columnInfo.setNullable(((String) map.get("Null")).equals("YES"));
			columnInfo.setPrimaryKey(((String) map.get("Key")).equals("PRI"));
			columnInfo.setType(((String) map.get("Type")).split("\\(")[0]);
			String type = (String) map.get("Type");
			String typeLength = type.contains("(") ? type.split("\\(")[1].split("\\)")[0] : "0";
			String[] typeLenArr = typeLength.split(",");
			
			columnInfo.setLength(Integer.valueOf(typeLenArr[0]));
			if (typeLenArr.length > 1) {
				columnInfo.setPointLength(Integer.valueOf(typeLenArr[1]));
			}else {
				columnInfo.setPointLength(0);
			}
			
			columnInfo.setComment((String) map.get("Comment"));
			Class<?> javaType = javaTypeConverter.convert(columnInfo.getType(), columnInfo.getLength(), columnInfo.getPointLength());
			columnInfo.setJavaType(javaType.getSimpleName());
			columnInfo.setFullPackageJavaType(javaType.getName());
			
			columnInfo.setPropertyName(propertyGenerator.generateProperty(columnInfo.getName()));
			
			ColumnComment columnComment = columnCommentParser.parse(columnInfo.getComment());
			columnInfo.setColumnComment(columnComment);
			
			return columnInfo;
		}).collect(Collectors.toList());
		
		return columnInfos;
	}

	@Override
	public String getCreateTable(String databaseName, String tableName) {
		String sql = "SHOW CREATE TABLE `" + databaseName + "." + tableName +"`";
		
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		
		List<String> createTableSqls = (List<String>) list.stream().map(map -> {
			
			return (String)map.get("Create Table");
		}).collect(Collectors.toList());
		
		return createTableSqls.get(0);
	}

}
