package com.dnfeitosa.codegraph.loaders.finders;

import com.dnfeitosa.codegraph.commandline.Terminal;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class ApplicationsFinder {

	private Terminal terminal;

	public ApplicationsFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<String> findApplicationsIn(String location) {
		return $(applicationsIn(location)).filter(new NotBuildFolderFilter()).filter(new IsDirectory(location));
	}

	private List<String> applicationsIn(String location) {
		return terminal.execute("ls", location);
	}
}
