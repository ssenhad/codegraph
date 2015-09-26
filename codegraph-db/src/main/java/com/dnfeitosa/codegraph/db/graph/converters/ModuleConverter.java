package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.db.graph.nodes.JarNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
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

	public Set<ModuleNode> toNodes(Collection<com.dnfeitosa.codegraph.core.model.Module> modules) {
		Set<ModuleNode> set = new HashSet<>();
		for (com.dnfeitosa.codegraph.core.model.Module module : modules) {
			set.add(toNode(module));
		}
		return set;
	}

	public List<com.dnfeitosa.codegraph.core.model.Module> fromNodes(Set<ModuleNode> nodes) {
        return nodes
            .parallelStream()
            .map(this::fromNode)
            .collect(Collectors.toList());
	}

	public ModuleNode toNode(com.dnfeitosa.codegraph.core.model.Module module) {
		ModuleNode node = new ModuleNode();
		node.setName(module.getName());
		node.setArtifacts(artifactConverter.toNodes(module.getExportTypes()));
		setDependencies(module, node);
		return node;
	}

	private void setDependencies(com.dnfeitosa.codegraph.core.model.Module module, ModuleNode node) {
        Set<JarNode> jarNodes = jarConverter.toNodes(module.getDependencies());
        node.setDependencies(jarNodes);
	}

	private Set<ModuleNode> toModules(List<Jar> dependencies) {
		Set<ModuleNode> moduleNodes = new HashSet<>();
		for (Jar dependency : notNull(dependencies)) {
			ModuleNode moduleNode = new ModuleNode();
			moduleNode.setName(dependency.getName());
			moduleNodes.add(moduleNode);
		}
		return moduleNodes;
	}

	public com.dnfeitosa.codegraph.core.model.Module fromNode(ModuleNode node) {
		LOGGER.trace(String.format("Converting node to module '%s'.", node.getName()));

		List<Jar> dependencies = jarConverter.fromNodes(node.getDependencies());

		Set<ArtifactType> artifacts = artifactConverter.fromNodes(node.getArtifacts());
        com.dnfeitosa.codegraph.core.model.Module module = new com.dnfeitosa.codegraph.core.model.Module(node.getName(), null, dependencies, artifacts);
        return module;
	}
}
