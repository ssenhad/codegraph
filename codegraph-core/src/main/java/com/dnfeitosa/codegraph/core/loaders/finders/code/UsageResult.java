package com.dnfeitosa.codegraph.core.loaders.finders.code;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

public class UsageResult {

	private final String fileName;
	private final String className;

	public UsageResult(String fileName, String className) {
		this.fileName = fileName;
		this.className = className;
	}

	public String getFileName() {
		return fileName;
	}

	public String getClassName() {
		return className;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", fileName, className);
	}

	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}
}