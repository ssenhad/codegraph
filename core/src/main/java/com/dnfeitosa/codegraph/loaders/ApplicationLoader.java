package com.dnfeitosa.codegraph.loaders;

import com.dnfeitosa.codegraph.model.Application;
import com.dnfeitosa.codegraph.model.Module;

import java.util.List;

import static com.dnfeitosa.codegraph.filesystem.Path.join;

public class ApplicationLoader {

	private ModulesLoader modulesLoader;

	public ApplicationLoader(ModulesLoader modulesLoader) {
		this.modulesLoader = modulesLoader;
	}

	public Application load(String codebaseRoot, String applicationName) {
		String location = join(codebaseRoot, applicationName);
		return new Application(applicationName, location, loadModules(location));
	}

	private List<Module> loadModules(String applicationLocation) {
		return modulesLoader.loadAllFrom(applicationLocation);
	}
}
