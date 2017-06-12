package com.dnfeitosa.codegraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VersionResource {

    private final String declared;
    private final String resolved;

    @JsonCreator
    public VersionResource(@JsonProperty("declared") String declared,
                           @JsonProperty("resolved") String resolved) {
        this.declared = declared;
        this.resolved = resolved;
    }

    public String getDeclared() {
        return declared;
    }

    public String getResolved() {
        return resolved;
    }
}
