package com.dnfeitosa.codegraph.core.models;

public class Project {

    private Long id;

    private String name;
    private String version;
    private String organization;

    public Project(String name, String organization, String version) {
        this(null, name, organization, version);
    }

    public Project(Long id, String name, String organization, String version) {
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

    public String getVersion() {
        return version;
    }

    public String getOrganization() {
        return organization;
    }
}
