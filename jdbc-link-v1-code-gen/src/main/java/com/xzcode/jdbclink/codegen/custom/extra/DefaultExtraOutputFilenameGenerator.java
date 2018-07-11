package com.xzcode.jdbclink.codegen.custom.extra;

import com.xzcode.jdbclink.codegen.model.TableEntityInfo;

public class DefaultExtraOutputFilenameGenerator implements IExtraTemplateOutputFilenameGenerator {

	@Override
	public String generate(String template, TableEntityInfo tableEntityInfo) {
		return tableEntityInfo.getEntityClassName() + ".extra";
	}

}
