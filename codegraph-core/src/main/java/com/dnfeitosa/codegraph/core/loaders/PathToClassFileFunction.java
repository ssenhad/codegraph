package com.dnfeitosa.codegraph.core.loaders;

import static org.apache.commons.lang.StringUtils.countMatches;

import com.dnfeitosa.codegraph.core.loaders.finders.code.ClassFile;
import com.dnfeitosa.coollections.Function;

public class PathToClassFileFunction implements Function<String, ClassFile> {

	private final String moduleName;

	public PathToClassFileFunction(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public ClassFile apply(String filePath) {
		String replace = removeExtension(filePath);
		String fullQualifiedName = replace.replace("/", ".");
		String name = getClassName(fullQualifiedName);
		String packageName = getPackageName(fullQualifiedName);
		ClassFile.FileType sourceType = getSourceType(filePath);
		return new ClassFile(name, packageName, moduleName, sourceType, filePath);
	}

	private String removeExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}

	private String getPackageName(String value) {
		if (countMatches(value, ".") <= 1) {
			return "";
		}
		return value.substring(value.indexOf('.') + 1, value.lastIndexOf('.'));
	}

	private ClassFile.FileType getSourceType(String fileName) {
		return fileName.startsWith("src") ? ClassFile.FileType.SRC : ClassFile.FileType.TEST;
	}

	private String getClassName(String value) {
		return value.substring(value.lastIndexOf('.') + 1);
	}
}
