package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.coollections.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

@Component
public class IvyFileFinder {

	private final Terminal terminal;

    @Autowired
	public IvyFileFinder(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<ModuleDescriptor> findFilesIn(String location) {
		List<String> files = terminal.execute("find", location, "-name", "ivy.xml");
		return $(files).map(toIvyFile());
	}

	private Function<String, ModuleDescriptor> toIvyFile() {
        return null;
//		return location -> new IvyFile(location);
	}
}
