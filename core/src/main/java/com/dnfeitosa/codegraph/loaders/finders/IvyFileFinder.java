package com.dnfeitosa.codegraph.loaders.finders;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.model.IvyFile;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class IvyFileFinder {

	private final Terminal terminal;

	public IvyFileFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<IvyFile> findFilesIn(String location) {
		List<String> files = terminal.execute("find", location, "-name", "ivy.xml");
		return $(files).map(toIvyFile());
	}

	private Function<String, IvyFile> toIvyFile() {
		return new Function<String, IvyFile>() {
			public IvyFile apply(String location) {
				return new IvyFile(location);
			}
		};
	}
}
