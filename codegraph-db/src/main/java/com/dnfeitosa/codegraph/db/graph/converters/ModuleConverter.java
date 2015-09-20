package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dnfeitosa.coollections.Coollections.notNull;

@Component
public class ModuleConverter {

	private static final Logger LOGGER = Logger.getLogger(ModuleConverter.class);

	private final JarConverter jarConverter;
	private final ArtifactConverter artifactConverter;

	@Autowired
	public ModuleConverter(JarConverter jarConverter, ArtifactConverter artifactConverter) {
		this.jarConverter = jarConverter;
		this.artifactConverter = artifactConverter;
	}

	public Set<Module> toNodes(Collection<com.dnfeitosa.codegraph.core.model.Module> modules) {
		Set<Module> set = new HashSet<>();
		for (com.dnfeitosa.codegraph.core.model.Module module : modules) {
			set.add(toNode(module));
		}
		return set;
	}

	public List<com.dnfeitosa.codegraph.core.model.Module> fromNodes(Set<Module> nodes) {
        return nodes
            .parallelStream()
            .map(this::fromNode)
            .collect(Collectors.toList());
	}

	public Module toNode(com.dnfeitosa.codegraph.core.model.Module module) {
		Module node = new Module();
		node.setName(module.getName());
		node.setArtifacts(artifactConverter.toNodes(module.getExportTypes()));
		setDependencies(module, node);
		return node;
	}

	private void setDependencies(com.dnfeitosa.codegraph.core.model.Module module, Module node) {
        Set<com.dnfeitosa.codegraph.db.graph.nodes.Jar> jars = jarConverter.toNodes(module.getDependencies());
        node.setDependencies(jars);
	}

	private Set<Module> toModules(List<Jar> dependencies) {
		Set<Module> modules = new HashSet<>();
		for (Jar dependency : notNull(dependencies)) {
			Module module = new Module();
			module.setName(dependency.getName());
			modules.add(module);
		}
		return modules;
	}

	public com.dnfeitosa.codegraph.core.model.Module fromNode(Module node) {
		LOGGER.trace(String.format("Converting node to module '%s'.", node.getName()));

		List<Jar> dependencies = jarConverter.fromNodes(node.getDependencies());

		Set<ArtifactType> artifacts = artifactConverter.fromNodes(node.getArtifacts());
		return new com.dnfeitosa.codegraph.core.model.Module(node.getName(), null, dependencies, artifacts);
	}
}
