package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.db.graph.nodes.ArtifactNode;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.dnfeitosa.coollections.Coollections.notNull;

@Component
public class ArtifactConverter {

	public Set<ArtifactNode> toNodes(Set<ArtifactType> exportTypes) {
        return notNull(exportTypes)
            .stream()
            .map(exportType -> toNode(exportType))
            .collect(Collectors.toSet());
	}

	public ArtifactNode toNode(ArtifactType exportType) {
		ArtifactNode artifactNode = new ArtifactNode();
        artifactNode.setName(exportType.getName());
		return artifactNode;
	}

	public Set<ArtifactType> fromNodes(Set<ArtifactNode> artifactNodes) {
        return notNull(artifactNodes)
            .stream()
            .map(artifact -> fromNode(artifact))
            .collect(Collectors.toSet());
	}

    public ArtifactType fromNode(ArtifactNode artifactNode) {
        return new ArtifactType(artifactNode.getName());
    }
}