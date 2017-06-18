package com.dnfeitosa.codegraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResolutionResultResource {

    private List<ResolvedDependency> dependencies;

    @JsonCreator
    public ResolutionResultResource(@JsonProperty("dependencies") List<ResolvedDependency> dependencies) {
        this.dependencies = dependencies;
    }
}
