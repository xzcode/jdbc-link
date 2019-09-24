package ${tableEntityInfo.fullPackageName};

import com.xzcode.jdbclink.core.annotations.Column;
import com.xzcode.jdbclink.core.annotations.Id;
import com.xzcode.jdbclink.core.annotations.Table;
import com.xzcode.jdbclink.core.annotations.Entity;
import com.xzcode.jdbclink.core.entity.IEntity;
import com.xzcode.jdbclink.core.entity.model.EntityField;

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
@Table(name = ${tableEntityInfo.entityClassName}.__TABLE_NAME__, alias = ${tableEntityInfo.entityClassName}.__TABLE_NAME__)
public class ${tableEntityInfo.entityClassName} implements IEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * ${tableEntityInfo.tableComment} 表名
	 */
	public static final String __TABLE_NAME__ = "${tableEntityInfo.tableName}";
	
	/**
	 * 表示所有列
	 */
	public static final EntityField ALL_ = new EntityField("*", "*", ${tableEntityInfo.entityClassName}.__TABLE_NAME__);

	//列名常量
	
	<#list tableEntityInfo.columns as col>  
	/**
	 * ${col.comment}
	 */
	public static final EntityField ${col.name?upper_case} = new EntityField("${col.name}", "${col.propertyName}", ${tableEntityInfo.entityClassName}.__TABLE_NAME__);
	
	</#list>
	
	
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
	@Column(name = "${col.name}")
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
