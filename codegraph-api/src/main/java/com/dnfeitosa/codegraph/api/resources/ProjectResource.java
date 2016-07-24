package com.dnfeitosa.codegraph.api.resources;

import java.util.List;

public class ProjectResource {

    private Long id;
    private String name;
    private String version;
    private String organization;

    private List<ArtifactResource> artifacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ArtifactResource> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<ArtifactResource> artifacts) {
        this.artifacts = artifacts;
    }
}
