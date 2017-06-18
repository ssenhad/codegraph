package com.dnfeitosa.codegraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ArtifactResource implements Resource {

    private String organization;
    private String name;
    private String version;
    private List<DeclaredDependency> dependencies;

    @JsonCreator
    public ArtifactResource(@JsonProperty("organization") String organization,
                            @JsonProperty("name") String name,
                            @JsonProperty("version") String version,
                            @JsonProperty("dependencies") List<DeclaredDependency> dependencies) {
        this.organization = organization;
        this.name = name;
        this.version = version;
        this.dependencies = dependencies;
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

    public List<DeclaredDependency> getDependencies() {
        return dependencies;
    }
}
