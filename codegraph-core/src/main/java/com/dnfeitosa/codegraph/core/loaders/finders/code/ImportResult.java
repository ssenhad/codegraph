package com.dnfeitosa.codegraph.core.loaders.finders.code;

public class ImportResult {

	private final String fileName;
	private final Import imported;

	public ImportResult(String fileName, Import imported) {
		this.fileName = fileName;
		this.imported = imported;
	}

	public String getFileName() {
		return fileName;
	}

	public Import getImported() {
		return imported;
	}
}