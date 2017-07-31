package com.dnfeitosa.codegraph.server.api.resources;

import java.util.Set;

public class ArtifactVersions {

    private final ArtifactResource artifact;
    private final Set<AvailableVersionResource> versions;

    public ArtifactVersions(ArtifactResource artifact, Set<AvailableVersionResource> versions) {
        this.artifact = artifact;
        this.versions = versions;
    }

    public ArtifactResource getArtifact() {
        return artifact;
    }

    public Set<AvailableVersionResource> getVersions() {
        return versions;
    }
}
