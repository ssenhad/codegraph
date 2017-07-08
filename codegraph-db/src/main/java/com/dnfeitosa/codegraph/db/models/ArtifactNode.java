package com.dnfeitosa.codegraph.db.models;

import com.dnfeitosa.codegraph.db.Node;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity(label = "Artifact")
public class ArtifactNode {

    @GraphId
    private Long _id;

    @Index(unique = true, primary = true)
    private String id;
    private String organization;
    private String name;
    private String version;

    @Relationship(type = "DECLARES")
    private Set<DeclaresRelationship> declaredDependencies;

    ArtifactNode() {
    }

    public ArtifactNode(String organization, String name, String version) {
        this.id = Node.id(organization, name, version);
        this.name = name;
        this.version = version;
        this.organization = organization;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getOrganization() {
        return organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactNode that = (ArtifactNode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addDependency(DependencyNode dependencyNode, Set<String> configurations) {
        getDeclaredDependencies().add(new DeclaresRelationship(this, dependencyNode, configurations));
    }

    public Set<DeclaresRelationship> getDeclaredDependencies() {
        if (declaredDependencies == null) {
            declaredDependencies = new HashSet<>();
        }
        return declaredDependencies;
    }

    public void setDeclaredDependencies(Set<DeclaresRelationship> declaredDependencies) {
        this.declaredDependencies = declaredDependencies;
    }

    public Set<DependencyNode> getDependencies() {
        return getDeclaredDependencies().stream()
            .map(DeclaresRelationship::getDependency)
            .collect(Collectors.toSet());
    }
}
