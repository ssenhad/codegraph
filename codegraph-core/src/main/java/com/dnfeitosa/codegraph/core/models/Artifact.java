package com.dnfeitosa.codegraph.core.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Artifact {

    private final String id;
    private final String organization;
    private final String name;
    private final Version version;

    private List<Dependency> dependencies = new ArrayList<>();
    private Set<Type> types = new HashSet<>();

    public Artifact(String name, String organization, Version version) {
        this.id = String.format("%s:%s:%s", organization, name, version.getNumber());
        this.name = name;
        this.organization = organization;
        this.version = version;
    }

    public String getId() {
        return id;
    }
    public Long _getId() {
        return null;
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

    public void addDependency(Artifact artifact) {
        throw new UnsupportedOperationException("artifact.addDependency(Artifact)");
    }

    public void addDependency(Dependency dependency) {
        this.dependencies.add(dependency);
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public List<Artifact> _getDependencies() {
        return null;
    }

    public void addType(Type type) {
        this.types.add(type);
    }

    public Set<Type> getTypes() {
        return types;
    }
}
