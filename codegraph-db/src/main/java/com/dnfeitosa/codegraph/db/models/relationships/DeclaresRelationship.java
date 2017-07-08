package com.dnfeitosa.codegraph.db.models.relationships;

import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.DependencyNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.Objects;
import java.util.Set;

@RelationshipEntity(type = "DECLARES")
public class DeclaresRelationship {

    @GraphId
    private Long _id;

    @StartNode
    private ArtifactNode artifact;

    @EndNode
    private DependencyNode dependency;

    private Set<String> configurations;

    public DeclaresRelationship() { }

    public DeclaresRelationship(ArtifactNode artifact, DependencyNode dependency, Set<String> configurations) {
        this.artifact = artifact;
        this.dependency = dependency;
        this.configurations = configurations;
    }

    public DependencyNode getDependency() {
        return dependency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeclaresRelationship that = (DeclaresRelationship) o;
        return Objects.equals(configurations, that.configurations) &&
            Objects.equals(artifact, that.artifact) &&
            Objects.equals(dependency, that.dependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configurations, artifact, dependency);
    }

    public Set<String> getConfigurations() {
        return configurations;
    }
}
