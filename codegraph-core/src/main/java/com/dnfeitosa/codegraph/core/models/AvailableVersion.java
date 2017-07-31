package com.dnfeitosa.codegraph.core.models;

import java.util.Arrays;
import java.util.Objects;

public class AvailableVersion {

    private final Version version;
    private final Availability[] availability;

    public AvailableVersion(Version version, Availability... availability) {
        this.version = version;
        this.availability = availability;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableVersion that = (AvailableVersion) o;
        return Objects.equals(version, that.version) &&
            Arrays.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, availability);
    }

    @Override
    public String toString() {
        return "AvailableVersion{" +
            "version=" + version +
            ", availability=" + Arrays.toString(availability) +
            '}';
    }

    public Availability[] getAvailability() {
        return availability;
    }

    public enum Availability {
        ARTIFACT,
        DEPENDENCY
    }
}
