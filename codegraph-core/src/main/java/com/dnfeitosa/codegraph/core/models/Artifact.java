package com.dnfeitosa.codegraph.core.models;

import java.util.ArrayList;
import java.util.List;

public class Artifact {

    private final String id;
    private final String organization;
    private final String name;
    private final Version version;

    private final List<Dependency> dependencies = new ArrayList<>();

    public Artifact(String organization, String name, Version version) {
        this.id = String.format("%s:%s:%s", organization, name, version.getNumber());
        this.name = name;
        this.organization = organization;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getOrganization() {
        return organization;
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public void addDependency(Dependency dependency) {
        this.dependencies.add(dependency);
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
