package com.dnfeitosa.codegraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResolvedDependency {

    private final String parent;
    private final String organization;
    private final String name;
    private final VersionResource version;

    @JsonCreator
    public ResolvedDependency(@JsonProperty("parent") String parent,
                              @JsonProperty("organization") String organization,
                              @JsonProperty("name") String name,
                              @JsonProperty("version") VersionResource version) {
        this.parent = parent;
        this.organization = organization;
        this.name = name;
        this.version = version;
    }

    public String getParent() {
        return parent;
    }

    public String getOrganization() {
        return organization;
    }

    public String getName() {
        return name;
    }

    public VersionResource getVersion() {
        return version;
    }
}
