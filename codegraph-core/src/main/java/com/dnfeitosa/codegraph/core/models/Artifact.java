/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dnfeitosa.codegraph.core.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.dnfeitosa.codegraph.core.utils.Arrays.asList;

public class Artifact implements Comparable<Artifact> {

    private final String id;
    private final String organization;
    private final String name;
    private final Version version;

    private final Set<Dependency> dependencies = new HashSet<>();

    public Artifact(String organization, String name, Version version) {
        this.id = id(organization, name, version);
        this.name = name;
        this.organization = organization;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getOrganization() {
        return organization;
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public Artifact addDependency(Dependency dependency, Dependency... dependencies) {
        this.dependencies.add(dependency);
        this.dependencies.addAll(asList(dependencies));
        return this;
    }

    public Set<Dependency> getDependencies() {
        return dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return Objects.equals(id, artifact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Artifact o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return id;
    }

    public static String id(String organization, String name, Version version) {
        return String.format("%s:%s:%s", organization, name, version.getNumber());
    }

    public String hash() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
}
