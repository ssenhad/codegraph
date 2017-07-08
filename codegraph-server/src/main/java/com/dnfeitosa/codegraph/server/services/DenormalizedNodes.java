package com.dnfeitosa.codegraph.server.services;

import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.DependencyNode;

import java.util.Set;

public class DenormalizedNodes {

    private final ArtifactNode artifact;
    private final Set<DependencyNode> dependencies;
    private final Set<DeclaresRelationship> dependencyRelationships;

    public DenormalizedNodes(ArtifactNode artifact,
                             Set<DependencyNode> dependencies,
                             Set<DeclaresRelationship> dependencyRelationships) {
        this.artifact = artifact;
        this.dependencies = dependencies;
        this.dependencyRelationships = dependencyRelationships;
    }

    public ArtifactNode getArtifact() {
        return artifact;
    }

    public Set<DependencyNode> getDependencies() {
        return dependencies;
    }

    public Set<DeclaresRelationship> getDependencRelationships() {
        return dependencyRelationships;
    }
}
