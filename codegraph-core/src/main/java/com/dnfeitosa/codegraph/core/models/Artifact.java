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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Artifact implements Comparable<Artifact> {

    private final String id;
    private final String organization;
    private final String name;
    private final Version version;

    private final List<Dependency> dependencies = new ArrayList<>();

    public Artifact(String organization, String name, Version version) {
        this.id = String.format("%s:%s:%s", organization, name, version.getNumber());
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

    public void addDependency(Dependency dependency) {
        this.dependencies.add(dependency);
    }

    public List<Dependency> getDependencies() {
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
}
