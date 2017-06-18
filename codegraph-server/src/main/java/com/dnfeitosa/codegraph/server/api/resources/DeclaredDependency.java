package com.dnfeitosa.codegraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class DeclaredDependency {

    private final String organization;
    private final String name;
    private final String version;
    private final Set<String> configurations;

    @JsonCreator
    public DeclaredDependency(@JsonProperty("organization") String organization,
                              @JsonProperty("name") String name,
                              @JsonProperty("version") String version,
                              @JsonProperty("configurations") Set<String> configurations) {
        this.organization = organization;
        this.name = name;
        this.version = version;
        this.configurations = configurations;
    }

    public String getOrganization() {
        return organization;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Set<String> getConfigurations() {
        return configurations;
    }
}
