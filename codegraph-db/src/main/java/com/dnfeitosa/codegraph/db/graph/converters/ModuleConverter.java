package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.notNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class ModuleConverter {

	private final JarConverter jarConverter;
	private final ArtifactConverter artifactConverter;

    public ModuleConverter() {
        this(new JarConverter(), new ArtifactConverter());
    }

    ModuleConverter(JarConverter jarConverter, ArtifactConverter artifactConverter) {
        this.jarConverter = jarConverter;
		this.artifactConverter = artifactConverter;
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
        node.setVersion(module.getVersion());
        setArtifacts(module, node);
        setDependencies(module, node);
		return node;
	}

    private void setArtifacts(Module module, ModuleNode node) {
        node.setArtifacts(artifactConverter.toNodes(module.getExportTypes()));
    }

    private void setDependencies(Module module, ModuleNode node) {
        node.setDependencies(jarConverter.toNodes(module.getDependencies()));
	}

	public Module fromNode(ModuleNode node) {
        if (node == null) {
            return null;
        }

        String name = node.getName();
        String organization = node.getOrganization();
        String version = node.getVersion();
		List<Jar> dependencies = jarConverter.fromNodes(node.getDependencies());
		Set<ArtifactType> artifacts = artifactConverter.fromNodes(node.getArtifacts());
        return new Module(name, organization, version, dependencies, artifacts);
	}
}
