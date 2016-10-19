package com.dnfeitosa.codegraph.core.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Artifact {

    private Long id;

    private String name;
    private Version version;
    private String organization;
    private String type;
    private String extension;

    private List<Artifact> dependencies = new ArrayList<>();
    private Set<Type> types = new HashSet<>();

    public Artifact(String name, String organization, Version version, String type, String extension) {
        this(null, name, organization, version, type, extension);
    }

    public Artifact(Long id, String name, String organization, Version version, String type, String extension) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.version = version;
        this.type = type;
        this.extension = extension;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public String getOrganization() {
        return organization;
    }

    public String getType() {
        return type;
    }

    public String getExtension() {
        return extension;
    }

    public void addDependency(Artifact artifact) {
        this.dependencies.add(artifact);
    }

    public List<Artifact> getDependencies() {
        return dependencies;
    }

    public void addType(Type type) {
        this.types.add(type);
    }

    public Set<Type> getTypes() {
        return types;
    }
}
