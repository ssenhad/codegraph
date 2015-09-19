package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

@Component
public class JavaFileFinder {

	private final Terminal terminal;

	@Autowired
	public JavaFileFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<String> findFilesIn(final String location) {
		List<String> files = executeFindIn(location);
        return $(files)
            .map(filePath -> filePath.substring(location.length() + 1));
	}

    private List<String> executeFindIn(String location) {
		return terminal.execute("find", location, "-name", "*.java");
	}
}