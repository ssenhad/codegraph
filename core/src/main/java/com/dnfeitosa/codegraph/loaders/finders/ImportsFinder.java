package com.dnfeitosa.codegraph.loaders.finders;

import static com.dnfeitosa.coollections.Coollections.$;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dnfeitosa.coollections.Filter;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.loaders.finders.code.ImportResult;

@Component
public class ImportsFinder {

	private final Terminal terminal;

	@Autowired
	public ImportsFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<ImportResult> findImportsIn(String moduleLocation) {
		List<String> imports = findImports(moduleLocation);
		return $(imports)
			.filter(notEmptyString())
			.filter(notCommented())
			.collect(trim())
			.map(toResult())
			.filter(notNull());

	}

	private Filter<ImportResult> notNull() {
		return new Filter<ImportResult>() {

			@Override
			public Boolean matches(ImportResult input) {
				return input != null;
			}
		};
	}

	private Function<String, String> trim() {
		return new Function<String, String>() {

			@Override
			public String apply(String input) {
				return input.trim();
			}
		};
	}

	private Filter<String> notCommented() {
		return new Filter<String>() {

			@Override
			public Boolean matches(String input) {
				return !input.startsWith("//");
			}
		};
	}

	private List<String> findImports(String moduleLocation) {
		return terminal.execute(new File(moduleLocation), "egrep", "-Ra", "--include=*.java", "import ", ".");
	}

	private Filter<String> notEmptyString() {
		return new Filter<String>() {
			@Override
			public Boolean matches(String input) {
				return StringUtils.isNotEmpty(input);
			}
		};
	}

	private Function<String, ImportResult> toResult() {
		return new StringToImportResult();
	}
}