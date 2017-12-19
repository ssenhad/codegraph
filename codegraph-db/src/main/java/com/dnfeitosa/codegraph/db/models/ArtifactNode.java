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
package com.dnfeitosa.codegraph.db.models;

import com.dnfeitosa.codegraph.db.Node;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity(label = "Artifact")
public class ArtifactNode {

    @GraphId
    private Long _id;

    @Index(unique = true, primary = true)
    private String id;
    private String organization;
    private String name;
    private String version;

    @Relationship(type = "DEPENDS_ON")
    private Set<DeclaresRelationship> declaredDependencies;

    ArtifactNode() {
    }

    public ArtifactNode(String organization, String name, String version) {
        this.id = Node.id(organization, name, version);
        this.name = name;
        this.version = version;
        this.organization = organization;
    }

    public String getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactNode that = (ArtifactNode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ArtifactNode addDependency(ArtifactNode dependencyNode, Set<String> configurations) {
        getDeclaredDependencies().add(new DeclaresRelationship(this, dependencyNode, configurations));
        return this;
    }

    public Set<DeclaresRelationship> getDeclaredDependencies() {
        if (declaredDependencies == null) {
            declaredDependencies = new HashSet<>();
        }
        return declaredDependencies;
    }

    public void setDeclaredDependencies(Set<DeclaresRelationship> declaredDependencies) {
        this.declaredDependencies = declaredDependencies;
    }

    public Set<ArtifactNode> getDependencies() {
        return getDeclaredDependencies().stream()
            .map(DeclaresRelationship::getDependency)
            .collect(Collectors.toSet());
    }

    public String toString() {
        if (declaredDependencies != null) {
            return String.format("%s:(%s)", id, declaredDependencies);
        }
        return id;
    }
}
