package com.dnfeitosa.codegraph.core.loaders;

import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.coollections.Function;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ModuleLoader implements Function<ModuleDescriptor, Module> {

	@Override
	public Module apply(ModuleDescriptor descriptor) {
		String moduleName = descriptor.getName();
		String location = descriptor.getLocation();
		String organization = descriptor.getOrganization();
		List<Jar> dependencies = descriptor.getDependencies();
		Set<ArtifactType> exportTypes = descriptor.getExportTypes();
		return new Module(moduleName, organization, null, dependencies, exportTypes);
	}
}