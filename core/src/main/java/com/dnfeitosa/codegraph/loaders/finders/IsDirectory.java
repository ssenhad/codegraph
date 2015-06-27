package com.dnfeitosa.codegraph.loaders.finders;

import com.dnfeitosa.coollections.Filter;

import java.io.File;

public class IsDirectory implements Filter<String> {

	private String location;

	public IsDirectory(String location) {
		this.location = location;
	}

	public Boolean matches(String s) {
		return new File(location, s).isDirectory();
	}
}