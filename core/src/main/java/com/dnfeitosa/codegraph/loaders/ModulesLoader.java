package com.dnfeitosa.codegraph.loaders;

import com.dnfeitosa.codegraph.loaders.finders.IvyFileFinder;
import com.dnfeitosa.codegraph.model.IvyFile;
import com.dnfeitosa.codegraph.model.Module;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class ModulesLoader {

	private final IvyFileFinder finder;
	private ModuleLoader moduleLoader;

	public ModulesLoader(IvyFileFinder finder, ModuleLoader moduleLoader) {
		this.finder = finder;
		this.moduleLoader = moduleLoader;
	}

	public List<Module> loadAllFrom(String applicationLocation) {
		return $(ivyFilesFrom(applicationLocation)).map(toModule());
	}

	private ModuleLoader toModule() {
		return moduleLoader;
	}

	private List<IvyFile> ivyFilesFrom(String applicationLocation) {
		return finder.findFilesIn(applicationLocation);
	}
}
