package com.dnfeitosa.codegraph.loaders;

import com.dnfeitosa.codegraph.model.ArtifactType;
import com.dnfeitosa.codegraph.model.Jar;
import com.dnfeitosa.codegraph.model.Module;
import com.dnfeitosa.codegraph.descriptors.ModuleDescriptor;
import com.dnfeitosa.coollections.Function;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ModuleLoader implements Function<ModuleDescriptor, Module> {

	@Override
	public Module apply(ModuleDescriptor ivyFile) {
		String moduleName = ivyFile.getName();
		String location = ivyFile.getLocation();
		List<Jar> dependencies = ivyFile.getDependencies();
		Set<ArtifactType> exportTypes = ivyFile.getExportTypes();
		return new Module(moduleName, location, dependencies, exportTypes);
	}
}