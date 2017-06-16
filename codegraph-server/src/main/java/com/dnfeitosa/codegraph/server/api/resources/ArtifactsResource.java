package com.dnfeitosa.codegraph.server.api.resources;

import java.util.List;

@Deprecated
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
}
