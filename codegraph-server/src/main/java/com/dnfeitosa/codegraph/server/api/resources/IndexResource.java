package com.dnfeitosa.codegraph.server.api.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IndexResource {

    private ArtifactResource artifact;
    private ResolutionResultResource resolutionResult;

    @JsonCreator
    public IndexResource(@JsonProperty("artifact") ArtifactResource artifact,
                         @JsonProperty("resolutionResult") ResolutionResultResource resolutionResult) {
        this.artifact = artifact;
        this.resolutionResult = resolutionResult;
    }

    public ArtifactResource getArtifact() {
        return artifact;
    }
}
