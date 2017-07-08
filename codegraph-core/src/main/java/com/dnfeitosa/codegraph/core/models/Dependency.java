package com.dnfeitosa.codegraph.core.models;

import java.util.Set;

public class Dependency {

    private final String id;
    private final String name;
    private final String organization;
    private final Version version;
    private final Set<String> configurations;

    public Dependency(String organization, String name, Version version, Set<String> configurations) {
        this.id = String.format("%s:%s:%s", organization, name, version.getNumber());
        this.name = name;
        this.organization = organization;
        this.version = version;
        this.configurations = configurations;
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

    public Set<String> getConfigurations() {
        return configurations;
    }
}
