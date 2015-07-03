package com.dnfeitosa.codegraph.db.graph.converters;

import static com.dnfeitosa.coollections.Coollections.notNull;

import java.util.HashSet;
import java.util.Set;

import com.dnfeitosa.codegraph.db.graph.nodes.Artifact;
import org.springframework.stereotype.Component;

import com.dnfeitosa.codegraph.core.model.ArtifactType;

@Component
public class ArtifactConverter {

	public Set<Artifact> toNodes(Set<ArtifactType> exportTypes) {
		Set<Artifact> artifacts = new HashSet<>();
		for (ArtifactType exportType : notNull(exportTypes)) {
			artifacts.add(toNode(exportType));
		}
		return artifacts;
	}

	public Artifact toNode(ArtifactType exportType) {
		Artifact artifact = new Artifact();
		artifact.setName(exportType.name());
		return artifact;
	}

	public Set<ArtifactType> fromNodes(Set<Artifact> artifacts) {
		Set<ArtifactType> artifactTypes = new HashSet<>();
		for (Artifact artifact : notNull(artifacts)) {
			artifactTypes.add(fromNode(artifact));
		}
		return artifactTypes;
	}

	public ArtifactType fromNode(Artifact artifact) {
		return ArtifactType.fromName(artifact.getName());
	}
}