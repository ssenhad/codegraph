package com.dnfeitosa.codegraph.core.loaders.finders.code;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

public class Import {

	private final String packageName;
	private final String className;

	public Import(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}

	public Import(String qualifiedName) {
		this(
			qualifiedName.substring(0, qualifiedName.lastIndexOf('.')),
			qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1));
	}

	@Override
	public boolean equals(Object o) {
		return reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return packageName + "." + className;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}
}