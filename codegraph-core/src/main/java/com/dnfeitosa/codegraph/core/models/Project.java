package com.dnfeitosa.codegraph.core.models;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private Long id;

    private String name;
    private Version version;
    private String organization;

    private List<Artifact> artifacts = new ArrayList<>();

    public Project(String name, String organization, Version version) {
        this(null, name, organization, version);
    }

    public Project(Long id, String name, String organization, Version version) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public String getOrganization() {
        return organization;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void addArtifact(Artifact artifact) {
        artifacts.add(artifact);
    }
}
