package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.coollections.Filter;

import java.io.File;

public class DirectoryFilter implements Filter<File> {

	private String location;

	public DirectoryFilter(String location) {
		this.location = location;
	}

	public Boolean matches(File s) {
		return s.isDirectory();
	}
}