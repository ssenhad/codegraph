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
package com.dnfeitosa.codegraph.db.models.relationships;

import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.Objects;
import java.util.Set;

@RelationshipEntity(type = "DECLARES")
public class DeclaresRelationship {

    @GraphId
    private Long _id;

    @StartNode
    private ArtifactNode artifact;

    @EndNode
    private ArtifactNode dependency;

    private Set<String> configurations;

    public DeclaresRelationship() { }

    public DeclaresRelationship(ArtifactNode artifact, ArtifactNode dependency, Set<String> configurations) {
        this.artifact = artifact;
        this.dependency = dependency;
        this.configurations = configurations;
    }

    public ArtifactNode getDependency() {
        return dependency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeclaresRelationship that = (DeclaresRelationship) o;
        return Objects.equals(configurations, that.configurations) &&
            Objects.equals(artifact, that.artifact) &&
            Objects.equals(dependency, that.dependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configurations, artifact, dependency);
    }

    public Set<String> getConfigurations() {
        return configurations;
    }
}
