package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.coollections.Filter;

class NotBuildFolderFilter implements Filter<String> {

	public Boolean matches(String applicationName) {
		return !"build".equalsIgnoreCase(applicationName);
	}
}
