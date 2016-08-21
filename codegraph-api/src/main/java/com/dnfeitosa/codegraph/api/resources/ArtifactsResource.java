package com.dnfeitosa.codegraph.api.resources;

import java.util.List;

public class ArtifactsResource {

    private List<ArtifactResource> artifacts;

    public ArtifactsResource() {
    }

    public ArtifactsResource(List<ArtifactResource> artifacts) {
        this.artifacts = artifacts;
    }

    public List<ArtifactResource> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<ArtifactResource> artifacts) {
        this.artifacts = artifacts;
    }
}
