package com.dnfeitosa.codegraph.core.models;

import java.util.Objects;

public class Version {

    private final String number;

    public Version(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public boolean isDynamic() {
        return number.endsWith("+");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return Objects.equals(number, version.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
