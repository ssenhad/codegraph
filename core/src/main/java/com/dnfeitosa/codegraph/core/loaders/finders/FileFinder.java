package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileFinder {

	private final Terminal terminal;

    @Autowired
	public FileFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<String> find(String location, String nameOrPattern) {
		return terminal.execute("find", location, "-name", nameOrPattern);
	}
}
