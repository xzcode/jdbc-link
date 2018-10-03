package com.xzcode.jdbclink.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xzcode.jdbclink.codegen.config.JdbcLinkEntityGeneratorConfig;
import com.xzcode.jdbclink.codegen.config.JdbcLinkExtraGeneratorConfig;
import com.xzcode.jdbclink.codegen.custom.clazz.DefaultEntityClassNameGenerator;
import com.xzcode.jdbclink.codegen.custom.clazz.IEntityClassNameGenerator;
import com.xzcode.jdbclink.codegen.custom.comment.column.DefaultColumnCommentParser;
import com.xzcode.jdbclink.codegen.custom.comment.column.IColumnCommentParser;
import com.xzcode.jdbclink.codegen.custom.datatype.DefaultDataTypeConverter;
import com.xzcode.jdbclink.codegen.custom.datatype.IDataTypeConverter;
import com.xzcode.jdbclink.codegen.custom.module.DefaultModuleNameGenerator;
import com.xzcode.jdbclink.codegen.custom.module.IModuleNameGenerator;
import com.xzcode.jdbclink.codegen.custom.property.DefaultEntityClassPropertyGenerator;
import com.xzcode.jdbclink.codegen.custom.property.IEntityClassPropertyGenerator;
import com.xzcode.jdbclink.codegen.data.adapter.IDatabaseInfoAdapter;
import com.xzcode.jdbclink.codegen.data.adapter.mysql.MysqlDatabaseInfoAdapter;
import com.xzcode.jdbclink.codegen.model.ExtraTemplateInfo;
import com.xzcode.jdbclink.codegen.model.TableEntityInfo;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * jdbclink代码生成器
 * 
 * 
 * @author zai
 * 2018-04-16
 */
public class JdbcLinkGenerator {
	
	
	private static final Logger looger = LoggerFactory.getLogger(JdbcLinkGenerator.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private JdbcLinkEntityGeneratorConfig entityGeneratorConfig;
	public void setConfig(JdbcLinkEntityGeneratorConfig entityGeneratorConfig) {
		this.entityGeneratorConfig = entityGeneratorConfig;
	}
	
	/**数据库信息数据适配器*/
	private IDatabaseInfoAdapter databaseInfoAdapter;
	
	
	
	
	
	
	/**模块包名生成器*/
	private IModuleNameGenerator moduleNameGenerator = new DefaultModuleNameGenerator();
	
	public void setModulePackageGenerator(IModuleNameGenerator moduleNameGenerator) {
		this.moduleNameGenerator = moduleNameGenerator;
	}
	
	private IEntityClassPropertyGenerator propertyGenerator = new DefaultEntityClassPropertyGenerator();
	
	public void setPropertyGenerator(IEntityClassPropertyGenerator propertyGenerator) {
		this.propertyGenerator = propertyGenerator;
	}
	
	private IEntityClassNameGenerator classNameGenerator = new DefaultEntityClassNameGenerator();
	public void setClassNameGenerator(IEntityClassNameGenerator classNameGenerator) {
		this.classNameGenerator = classNameGenerator;
	}
	
	
	private IDataTypeConverter javaTypeConverter = new DefaultDataTypeConverter();
	public void setJavaTypeConverter(IDataTypeConverter javaTypeConverter) {
		this.javaTypeConverter = javaTypeConverter;
	}
	
	private IColumnCommentParser columnCommentParser = new DefaultColumnCommentParser();
	public void setColumnCommentParser(IColumnCommentParser columnCommentParser) {
		this.columnCommentParser = columnCommentParser;
	}
	
	
	
	
	public JdbcLinkGenerator(JdbcLinkEntityGeneratorConfig config, JdbcTemplate jdbcTemplate) {
		super();
		this.entityGeneratorConfig = config;
		this.jdbcTemplate = jdbcTemplate;
		
		this.databaseInfoAdapter = getDatabaseInfoAdapter();
	}
	
	/**
	 * 获取实体类模版配置
	 * @return
	 * 
	 * @author zai
	 * 2018-04-19
	 */
	private Configuration getEntityTemplateConfig() {
		
		/**生成实体类freemarker 配置*/
		Configuration entityTemplateConfig =  new Configuration(Configuration.VERSION_2_3_0);;
		entityTemplateConfig.setDefaultEncoding("UTF-8");
		entityTemplateConfig.setClassForTemplateLoading(JdbcLinkGenerator.class, "/jdbclink_default_templates");
		
		return entityTemplateConfig;
		
	}
	
	/**
	 * 获取额外模版配置
	 * @return
	 * 
	 * @author zai
	 * 2018-04-19
	 */
	private Configuration getExtraTemplateConfig(Class<?> loaderClass, String templatePath) {
		
		/**额外模版freemarker 配置*/
		Configuration extraTemplateConfig =  new Configuration(Configuration.VERSION_2_3_0);;
		extraTemplateConfig.setDefaultEncoding("UTF-8");
		extraTemplateConfig.setClassForTemplateLoading(loaderClass, templatePath);
		return extraTemplateConfig;
		
	}

	
	/**
	 * 根据数据源url获取数据库类型，并返回对应的适配器
	 * @return
	 * 
	 * @author zai
	 * 2018-04-16
	 */
	private IDatabaseInfoAdapter getDatabaseInfoAdapter() {
		String dbUrl = entityGeneratorConfig.getDbUrl();
		
		String[] arr = dbUrl.toLowerCase().split(":");
		String databaseType = arr[1];
		
		switch (databaseType) {
		case "mysql":
			MysqlDatabaseInfoAdapter mysqlDatabaseInfoAdapter = new MysqlDatabaseInfoAdapter(jdbcTemplate, entityGeneratorConfig, propertyGenerator, javaTypeConverter, columnCommentParser, classNameGenerator, moduleNameGenerator);
			
			return mysqlDatabaseInfoAdapter;

		default:
			throw new RuntimeException("Unsupported database type: " + databaseType);
		}
		
	}
	
	/**
	 * 生成实体类文件
	 * 
	 * 
	 * @author zai
	 * 2018-04-16
	 */
	public void generateEntities(boolean coverOldFiles) {
		
		try {
			
			List<TableEntityInfo> tableEntityInfos = databaseInfoAdapter.getTableInfos(entityGeneratorConfig.getEntityDatabaseName());
			Configuration entityTemplateConfig = getEntityTemplateConfig();
			Template template = entityTemplateConfig.getTemplate("entity-java.ftl");
			
			for (TableEntityInfo tableEntityInfo : tableEntityInfos) {
				String modulePackageName = moduleNameGenerator.generateModulePackageName(tableEntityInfo.getTableName(), null, null);
				//String packagePath = entityGeneratorConfig.getEntityBasicPackage().replaceAll("\\.", "/");
				String entityBasicOutputPath = entityGeneratorConfig.getEntityBasicOutputPath();
				if (entityGeneratorConfig.isCreateModulePackage()) {
					entityBasicOutputPath += "/" + modulePackageName;
				}
				File outDir = new File(entityBasicOutputPath);
				if (!outDir.exists()) {
					outDir.mkdirs();
				}
				String outFilePath = entityBasicOutputPath + "/" + tableEntityInfo.getEntityClassName() + ".java";
				File outFile = new File(outFilePath);
				
				//覆盖或跳过
				if (outFile.exists() && !coverOldFiles) {
					looger.info("\nEntity java file ({}) exists,\npath:{} ,\nskip...", tableEntityInfo.getEntityClassName(), outFilePath);
					continue;
				}
				try(
					OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFilePath));
				){
					Map<String, Object> dataModel = new HashMap<>();
					dataModel.put("tableEntityInfo", tableEntityInfo);
					template.process(dataModel, writer);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 生成但不覆盖旧文件
	 * 
	 * 
	 * @author zai
	 * 2018-09-27 15:35:59
	 */
	public void generateEntities() {
		generateEntities(false);
	}
	
	/**
	 * 获取默认的额外模版生成配置
	 * @return
	 * 
	 * @author zai
	 * 2018-04-19
	 */
	private JdbcLinkExtraGeneratorConfig getDefaultJdbcLinkExtraGeneratorConfig() {
		
		JdbcLinkExtraGeneratorConfig generatorConfig = new JdbcLinkExtraGeneratorConfig();
		generatorConfig.setDatabaseName(entityGeneratorConfig.getEntityDatabaseName());
		generatorConfig.setDbUrl(entityGeneratorConfig.getDbUrl());
		generatorConfig.setDbUserName(entityGeneratorConfig.getDbUserName());
		generatorConfig.setDbPassword(entityGeneratorConfig.getDbPassword());
		
		return generatorConfig;
	}
	
	/**
	 * 根据表信息和自定义模版生成文件
	 * @param basicOutputPath
	 * @param templatePath
	 * @param templateLoaderClass
	 * @param templateInfos
	 * @param extraData
	 * 
	 * @author zai
	 * 2018-04-19
	 */
	public void generateTableInfosExtra(
			String templatePath, 
			Class<?> templateLoaderClass,
			List<ExtraTemplateInfo> templateInfos, 
			Map<String, Object> extraData,
			boolean coverOldFiles
			) {
		JdbcLinkExtraGeneratorConfig generatorConfig = getDefaultJdbcLinkExtraGeneratorConfig();
		generatorConfig.setTemplatePath(templatePath);
		generatorConfig.setTemplateLoaderClass(templateLoaderClass);
		generatorConfig.setTemplateInfos(templateInfos);
		generatorConfig.setExtraData(extraData);
		generatorConfig.setCoverOldFiles(coverOldFiles);
		generateTableInfosExtra(generatorConfig);
	}
	
	/**
	 * 根据表信息和自定义模版生成文件(不覆盖旧文件)
	 * @param templatePath
	 * @param templateLoaderClass
	 * @param templateInfos
	 * @param extraData
	 * 
	 * @author zai
	 * 2018-09-27 20:46:32
	 */
	public void generateTableInfosExtra(
			String templatePath, 
			Class<?> templateLoaderClass,
			List<ExtraTemplateInfo> templateInfos, 
			Map<String, Object> extraData
			) {
		JdbcLinkExtraGeneratorConfig generatorConfig = getDefaultJdbcLinkExtraGeneratorConfig();
		generatorConfig.setTemplatePath(templatePath);
		generatorConfig.setTemplateLoaderClass(templateLoaderClass);
		generatorConfig.setTemplateInfos(templateInfos);
		generatorConfig.setExtraData(extraData);
		generateTableInfosExtra(generatorConfig );
	}
	
	/**
	 * 获取所有实体表数据
	 * @return
	 * 
	 * @author zai
	 * 2018-04-22
	 */
	public List<TableEntityInfo> getTableEntityInfos() {
		return databaseInfoAdapter.getTableInfos(entityGeneratorConfig.getEntityDatabaseName());
	}
	
	/**
	 * 根据表信息和自定义模版生成文件
	 * @param generatorConfig
	 * 
	 * @author zai
	 * 2018-04-19
	 */
	public void generateTableInfosExtra(
			JdbcLinkExtraGeneratorConfig generatorConfig
			) {
		
		try {
			
			//获取表实体信息
			List<TableEntityInfo> tableEntityInfos = databaseInfoAdapter.getTableInfos(entityGeneratorConfig.getEntityDatabaseName());
			
			Configuration extraTemplateConfig = getExtraTemplateConfig(generatorConfig.getTemplateLoaderClass(), generatorConfig.getTemplatePath());
			for (TableEntityInfo tableEntityInfo : tableEntityInfos) {
				
				for (ExtraTemplateInfo templateInfo : generatorConfig.getTemplateInfos()) {
					
					Template template = extraTemplateConfig.getTemplate(templateInfo.getTemplate());
					
					String modulePackageName = moduleNameGenerator.generateModulePackageName(tableEntityInfo.getTableName(), null, null);
					
					String dirPart = templateInfo.getDirPartGenerator().generate(
							templateInfo.getTemplate(), 
							tableEntityInfo
							);
					
					String basicOutputPath = templateInfo.getBasicOutputPath() + "/" + modulePackageName + "/" + dirPart ;
					File outDir = new File(basicOutputPath);
					if (!outDir.exists()) {outDir.mkdirs();}
					
					String fileName = templateInfo.getOutputFilenameGenerator().generate(
							templateInfo.getTemplate(), 
							tableEntityInfo
							);
					
					
					Map<String, Object> dataModel = templateInfo.getDataGenerator().generate(tableEntityInfo);
					
					String outFilePath = basicOutputPath + "/" + fileName;
					
					File outFile = new File(outFilePath);
					if (outFile.exists()) {
						looger.info("\nGenerating file ({}) exists,\npath:{} ,\nskip...", tableEntityInfo.getEntityClassName(), outFilePath);
						continue;
					}
					
					try(
						OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFilePath));
					){
						template.process(dataModel, writer);
					}
				
				}
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 自定数据和模版进行文件生成
	 * @param basicOutputPath
	 * @param templatePath
	 * @param template
	 * @param templateLoaderClass
	 * @param outputFilename
	 * @param data
	 * 
	 * @author zai
	 * 2018-04-19
	 */
	public void generateCustom(
			String basicOutputPath, 
			String templatePath, 
			String template,
			Class<?> templateLoaderClass,
			String outputFilename,
			Map<String, Object> data,
			boolean coverOldFiles
			) {
			
			File outDir = new File(basicOutputPath);
			if (!outDir.exists()) {outDir.mkdirs();}
			
			String outFilePath = basicOutputPath + "/" + outputFilename;
			
			File outFile = new File(outFilePath);
			if (outFile.exists() && !coverOldFiles) {
				looger.info("\nGenerating file ({}) exists,\npath:{} ,\nskip...", outputFilename, outFilePath);
				return;
			}
			
			try(
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFilePath));
			){
				Template templateObj = getExtraTemplateConfig(templateLoaderClass, templatePath).getTemplate(template);
				templateObj.process(data, writer);
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			
		
	}
	
	/**
	 * 自定数据和模版进行文件生成(不覆盖旧文件)
	 * @param basicOutputPath
	 * @param templatePath
	 * @param template
	 * @param templateLoaderClass
	 * @param outputFilename
	 * @param data
	 * 
	 * @author zai
	 * 2018-09-28 10:10:42
	 */
	public void generateCustom(
			String basicOutputPath, 
			String templatePath, 
			String template,
			Class<?> templateLoaderClass,
			String outputFilename,
			Map<String, Object> data
			) {
		generateCustom(basicOutputPath, templatePath, template, templateLoaderClass, outputFilename, data, false);
	}

}
