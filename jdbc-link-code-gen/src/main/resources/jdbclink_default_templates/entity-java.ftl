package ${tableEntityInfo.fullPackageName};

import com.xzcode.jdbclink.core.annotations.Column;
import com.xzcode.jdbclink.core.annotations.Id;
import com.xzcode.jdbclink.core.annotations.Table;
import com.xzcode.jdbclink.core.annotations.Entity;
<#list tableEntityInfo.importJavaTypes as imp>  
import ${imp};
</#list>

/**
 * ${tableEntityInfo.tableComment} 实体类
 * 
 * @author JdbcLinkGenerator
 * ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@Entity
@Table(name = "${tableEntityInfo.tableName}")
public class ${tableEntityInfo.entityClassName} {

	/**
	 * ${tableEntityInfo.tableComment} 表名
	 */
	public static final String __ = "${tableEntityInfo.tableName}";

	//列名常量
	
	<#list tableEntityInfo.columns as col>  
	/**
	 * ${col.comment}
	 */
	public static final String ${col.name?upper_case} = "${col.name}";
	
	</#list>
	
	/**
	 * 属性名称
	 */
	public static interface PropName {
		<#list tableEntityInfo.columns as col>   
		/**
		 * ${col.comment} -- (属性名称) 
		 */
		String ${col.name?upper_case} = "${col.propertyName}";
		</#list>
	};
	
<#list tableEntityInfo.columns as col>   
	<#if col.columnComment??>
		<#if col.columnComment.enumProperties??>
		/**
		 * ${col.columnComment.clearComment} 常量类 
		 */
		public static interface ${col.propertyName?cap_first}Constant {
			<#list col.columnComment.enumProperties as enumProp>  
			/**
			 * ${enumProp.desc}   
			 */
			${enumProp.dataType} ${enumProp.name?upper_case} = ${enumProp.value};
			</#list>
		};
		</#if>
	</#if>
</#list>
	


<#list tableEntityInfo.columns as col>  
	/**
	 * ${col.comment}
	 */
	<#if col.isPrimaryKey>@Id</#if>
	@Column(name = ${col.name?upper_case})
	private ${col.javaType} ${col.propertyName};
	
</#list>



<#list tableEntityInfo.columns as col>  
	
	
	/**
	 * ${col.comment}
	 */
	public ${col.javaType} get${col.propertyName?cap_first}() {
		return this.${col.propertyName};	
	}
	
	/**
	 * ${col.comment}
	 */
	public ${tableEntityInfo.entityClassName} set${col.propertyName?cap_first}(${col.javaType} ${col.propertyName}) {
		this.${col.propertyName} = ${col.propertyName};
		return this;	
	}
	
</#list>
	
	

}
