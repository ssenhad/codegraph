package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.db.graph.nodes.Artifact;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.dnfeitosa.coollections.Coollections.notNull;

@Component
public class ArtifactConverter {

	public Set<Artifact> toNodes(Set<ArtifactType> exportTypes) {
        return notNull(exportTypes)
            .stream()
            .map(exportType -> toNode(exportType))
            .collect(Collectors.toSet());
	}

	public Artifact toNode(ArtifactType exportType) {
		Artifact artifact = new Artifact();
        artifact.setName(exportType.getName());
		return artifact;
	}

	public Set<ArtifactType> fromNodes(Set<Artifact> artifacts) {
        return notNull(artifacts)
            .stream()
            .map(artifact -> fromNode(artifact))
            .collect(Collectors.toSet());
	}

    public ArtifactType fromNode(Artifact artifact) {
        return new ArtifactType(artifact.getName());
    }
}