package com.sourcemuch.commons.jdbclink.generator.custom.extra;

import com.sourcemuch.commons.jdbclink.generator.model.TableEntityInfo;

public class DefaultExtraOutputFilenameGenerator implements IExtraTemplateOutputFilenameGenerator {

	@Override
	public String generate(String template, TableEntityInfo tableEntityInfo) {
		return tableEntityInfo.getEntityClassName() + ".extra";
	}

}
