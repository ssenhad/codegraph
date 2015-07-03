package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.core.loaders.finders.code.UsageResult;

class StringToUsageResult implements Function<String, UsageResult> {

	private String previousFileName;

	@Override
	public UsageResult apply(String input) {
		if (input.contains(":")) {
			String[] split = input.split(":");
			previousFileName = cleanDotSlash(split[0]);
			return new UsageResult(previousFileName, split[1]);
		}
		if (previousFileName == null) {
			throw new IllegalStateException("[BUG] No previous file information: " + input);
		}
		return new UsageResult(previousFileName, input);
	}

	private String cleanDotSlash(String fullFileName) {
		return fullFileName.replace("./", "");
	}
}