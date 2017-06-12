package com.dnfeitosa.codegraph.server.api.resources;

import java.util.ArrayList;
import java.util.List;

public class DependencyResource implements Resource {

    private String name;
    private String organization;
    private VersionResource version;
    private List<String> configurations = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public VersionResource getVersion() {
        return version;
    }

    public void setVersion(VersionResource version) {
        this.version = version;
    }

    public List<String> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<String> configurations) {
        this.configurations = configurations;
    }

    @Override
    public String getUri() {
        return null;
    }
}
