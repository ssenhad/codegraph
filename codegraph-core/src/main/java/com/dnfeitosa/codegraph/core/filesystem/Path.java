package com.dnfeitosa.codegraph.core.filesystem;

import org.apache.commons.lang.StringUtils;

public class Path {

	public static String join(String... parts) {
		return StringUtils.join(parts, "/");
	}
}
