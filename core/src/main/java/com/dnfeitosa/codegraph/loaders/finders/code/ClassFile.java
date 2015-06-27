package com.dnfeitosa.codegraph.loaders.finders.code;

import static java.lang.String.format;
import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClassFile {

	private final String name;
	private final String packageName;
	private final String moduleName;
	private final FileType sourceType;
	private final String path;
	private final Set<Import> imports = new HashSet<>();

	public ClassFile(String name, String packageName, String moduleName, FileType sourceType, String path) {
		this.name = name;
		this.packageName = packageName;
		this.moduleName = moduleName;
		this.sourceType = sourceType;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getPackageName() {
		return packageName;
	}

	public FileType getFileType() {
		return sourceType;
	}

	public String getQualifiedName() {
		return format("%s.%s", packageName, name);
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getPath() {
		return path;
	}

	public void addImport(Import _import) {
		imports.add(_import);
	}

	public void addImports(Collection<Import> classImports) {
		imports.addAll(classImports);
	}

	@Override
	public String toString() {
		return getQualifiedName();
	}

	@Override
	public boolean equals(Object o) {
		return reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	public static enum FileType {
		TEST, SRC;
	}

	public Set<Import> getImports() {
		return imports;
	}

	public Set<String> getUsedClasses() {
		return null;
	}
}