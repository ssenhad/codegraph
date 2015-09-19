package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

@Component
public class ApplicationsFinder {

	private Terminal terminal;

    @Autowired
	public ApplicationsFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<String> findApplicationsIn(String location) {
		return $(applicationsIn(location));
	}

	private List<String> applicationsIn(String location) {
		return terminal.execute("ls", location);
	}
}
