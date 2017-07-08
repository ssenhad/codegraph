package com.dnfeitosa.codegraph.db.models;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Objects;

import static com.dnfeitosa.codegraph.db.Node.id;

@NodeEntity(label = "Dependency")
public class DependencyNode {

    @GraphId
    private Long _id;

    @Index(unique = true, primary = true)
    private String id;
    private String organization;
    private String name;
    private String version;

    DependencyNode() {
    }

    public DependencyNode(String organization, String name, String version) {
        this.id = id(organization, name, version);
        this.organization = organization;
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getOrganization() {
        return organization;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyNode that = (DependencyNode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
