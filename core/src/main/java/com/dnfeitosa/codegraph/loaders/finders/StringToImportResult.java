package com.dnfeitosa.codegraph.loaders.finders;

import java.util.regex.Pattern;

import com.dnfeitosa.codegraph.loaders.finders.code.Import;
import org.apache.log4j.Logger;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.loaders.finders.code.ImportResult;

class StringToImportResult implements Function<String, ImportResult> {

	private static final Logger LOGGER = Logger.getLogger(StringToImportResult.class);

	@Override
	public ImportResult apply(String result) {
		try {
			String[] split = result.split(":");
			String fullFileName = split[0];
			String importStatement = split[1].trim();

			String fileName = cleanDotSlash(fullFileName);

			return new ImportResult(fileName, getImport(importStatement));
		} catch (Exception e) {
			LOGGER.warn("[BUG] Unable to convert import result: " + result);
			return null;
		}
	}

	private String cleanDotSlash(String fullFileName) {
		return fullFileName.replace("./", "");
	}

	private Import getImport(String statement) {
		if (statement.contains("static ")) {
			return toStaticImport(statement);
		}
		if (statement.endsWith("*;") && !matchUpper(statement)) {
			LOGGER.warn(String.format("Package import found: '%s'", statement));
			return null;
		}
		return toClassImport(statement);
	}

	private boolean matchUpper(String statement) {
		return Pattern.matches(".*[A-Z]+.*", statement);
	}

	private Import toStaticImport(String statement) {
		try {
			String sanitized = sanitize(statement);
			return new Import(getClassName(sanitized));
		} catch (Exception e) {
			LOGGER.warn(String.format("Unparseable import: '%s'", statement));
			return null;
		}
	}

	private String getClassName(String staticImport) {
		try {
			return staticImport.substring(0, staticImport.lastIndexOf("."));
		} catch (Exception e) {
			LOGGER.warn(String.format("Unparseable import: '%s'", staticImport));
			return null;
		}
	}

	private Import toClassImport(String importStatement) {
		try {
			return new Import(sanitize(importStatement.replace(".*", "")));
		} catch (Exception e) {
			LOGGER.warn(String.format("Unparseable import: '%s'", importStatement));
			return null;
		}
	}

	private String sanitize(String string) {
		return string
			.replace("import ", "")
			.replace("static ", "")
			.replace(";", "");
	}
}