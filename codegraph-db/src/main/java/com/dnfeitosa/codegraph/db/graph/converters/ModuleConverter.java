package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.apache.log4j.Logger;
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

    public ModuleConverter() {
        this(new JarConverter(), new ArtifactConverter());
    }

    ModuleConverter(JarConverter jarConverter, ArtifactConverter artifactConverter) {
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
        node.setOrganization(module.getOrganization());
		node.setArtifacts(artifactConverter.toNodes(module.getExportTypes()));
		setDependencies(module, node);
		return node;
	}

	private void setDependencies(com.dnfeitosa.codegraph.core.model.Module module, ModuleNode node) {
        Set<ModuleNode> jarNodes = jarConverter.toNodes(module.getDependencies());
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
        if (node == null) {
            return null;
        }

        LOGGER.trace(String.format("Converting node to module '%s'.", node.getName()));

		List<Jar> dependencies = jarConverter.fromNodes(node.getDependencies());

		Set<ArtifactType> artifacts = artifactConverter.fromNodes(node.getArtifacts());
        Module module = new Module(node.getName(), null, dependencies, artifacts);
        return module;
	}
}
