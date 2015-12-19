package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.notNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class ModuleConverter {

	private static final Logger LOGGER = Logger.getLogger(ModuleConverter.class);

	private final JarConverter jarConverter;
	private final ArtifactConverter artifactConverter;
	private final ApplicationConverter applicationConverter;

    public ModuleConverter() {
        this(new JarConverter(), new ArtifactConverter());
    }

    ModuleConverter(JarConverter jarConverter, ArtifactConverter artifactConverter) {
        this.jarConverter = jarConverter;
		this.artifactConverter = artifactConverter;
        this.applicationConverter = new ApplicationConverter(this);
    }

	public Set<ModuleNode> toNodes(Collection<Module> modules) {
        return modules.stream()
            .map(this::toNode)
            .collect(toSet());
	}

	public List<Module> fromNodes(Set<ModuleNode> nodes) {
        return notNull(nodes).stream()
            .map(this::fromNode)
            .collect(toList());
	}

	public ModuleNode toNode(Module module) {
		ModuleNode node = new ModuleNode();
		node.setName(module.getName());
        node.setOrganization(module.getOrganization());
		node.setArtifacts(artifactConverter.toNodes(module.getExportTypes()));
		setDependencies(module, node);
		return node;
	}

	private void setDependencies(Module module, ModuleNode node) {
        Set<ModuleNode> jarNodes = jarConverter.toNodes(module.getDependencies());
        node.setDependencies(jarNodes);
	}

	public Module fromNode(ModuleNode node) {
        if (node == null) {
            return null;
        }

		List<Jar> dependencies = jarConverter.fromNodes(node.getDependencies());

		Set<ArtifactType> artifacts = artifactConverter.fromNodes(node.getArtifacts());
        String organization = null;
        return new Module(node.getName(), organization, dependencies, artifacts);
	}
}
