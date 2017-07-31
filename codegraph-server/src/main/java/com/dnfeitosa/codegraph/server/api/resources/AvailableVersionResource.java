package com.dnfeitosa.codegraph.server.api.resources;

import java.util.Set;

public class AvailableVersionResource {

    private final String version;
    private final Set<String> availability;

    public AvailableVersionResource(String version, Set<String> availability) {
        this.version = version;
        this.availability = availability;
    }

    public String getVersion() {
        return version;
    }

    public Set<String> getAvailability() {
        return availability;
    }
}
