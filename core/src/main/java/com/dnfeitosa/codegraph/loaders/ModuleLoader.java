package com.dnfeitosa.codegraph.loaders;

import java.util.List;
import java.util.Set;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.model.ArtifactType;
import com.dnfeitosa.codegraph.model.IvyFile;
import com.dnfeitosa.codegraph.model.Jar;
import com.dnfeitosa.codegraph.model.Module;

public class ModuleLoader implements Function<IvyFile, Module> {

	@Override
	public Module apply(IvyFile ivyFile) {
		String moduleName = ivyFile.getModuleName();
		String location = ivyFile.getLocation();
		List<Jar> dependencies = ivyFile.getDependencies();
		Set<ArtifactType> exportTypes = ivyFile.getExportTypes();
		return new Module(moduleName, location, dependencies, exportTypes);
	}
}