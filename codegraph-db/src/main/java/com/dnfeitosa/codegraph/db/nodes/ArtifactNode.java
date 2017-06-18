package com.dnfeitosa.codegraph.db.nodes;

import java.util.HashSet;
import java.util.Set;

public class ArtifactNode {

    private Long id;

    private String name;
    private String version;
    private String organization;
    private String extension;
    private String type;

    private Set<ArtifactNode> dependencies;

    private Set<TypeNode> types;

    public ArtifactNode() {
    }

    public ArtifactNode(Long id, String name, String organization, String version, String type, String extension) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.organization = organization;
        this.type = type;
        this.extension = extension;
    }

    public Long getId() {
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

    public String getExtension() {
        return extension;
    }

    public String getType() {
        return type;
    }

    public Set<ArtifactNode> getDependencies() {
        if (dependencies == null) {
            dependencies = new HashSet<>();
        }
        return dependencies;
    }

    public void addDependency(ArtifactNode dependency) {
        getDependencies().add(dependency);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addType(TypeNode type) {
        getTypes().add(type);
    }

    public Set<TypeNode> getTypes() {
        if (types == null) {
            types = new HashSet<>();
        }
        return types;
    }
}
