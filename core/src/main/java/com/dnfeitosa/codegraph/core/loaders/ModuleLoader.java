package com.dnfeitosa.codegraph.core.loaders;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
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