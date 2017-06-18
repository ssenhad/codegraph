package com.dnfeitosa.codegraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResolvedDependency {

    private final String parent;
    private final String organization;
    private final String name;
    private final String version;

    @JsonCreator
    public ResolvedDependency(@JsonProperty("parent") String parent,
                              @JsonProperty("organization") String organization,
                              @JsonProperty("name") String name,
                              @JsonProperty("version") String version) {
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

    public String getVersion() {
        return version;
    }
}
