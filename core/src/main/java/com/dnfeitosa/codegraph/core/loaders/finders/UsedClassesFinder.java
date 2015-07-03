package com.dnfeitosa.codegraph.core.loaders.finders;

import static com.dnfeitosa.coollections.Coollections.$;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.loaders.finders.code.UsageResult;

@Component
public class UsedClassesFinder {

	private static final String CLASS_NAME_REGEX = "[A-Z]+[a-z][A-Za-z0-9]+";

	private final Terminal terminal;

	@Autowired
	public UsedClassesFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public Set<UsageResult> findUsedClassIn(String moduleLocation) {
		List<String> results = findClassUsages(moduleLocation);
		return $(results)
			.map(new StringToUsageResult())
			.unique();
	}

	private List<String> findClassUsages(String moduleLocation) {
		return terminal.execute(new File(moduleLocation), "egrep", "-Row", "--include=*.java", CLASS_NAME_REGEX, ".");
	}
}
