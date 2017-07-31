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
package com.dnfeitosa.codegraph.server.services;

import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.DependencyNode;

import java.util.Set;

public class DenormalizedNodes {

    private final ArtifactNode artifact;
    private final Set<DependencyNode> dependencies;
    private final Set<DeclaresRelationship> dependencyRelationships;

    public DenormalizedNodes(ArtifactNode artifact,
                             Set<DependencyNode> dependencies,
                             Set<DeclaresRelationship> dependencyRelationships) {
        this.artifact = artifact;
        this.dependencies = dependencies;
        this.dependencyRelationships = dependencyRelationships;
    }

    public ArtifactNode getArtifact() {
        return artifact;
    }

    public Set<DependencyNode> getDependencies() {
        return dependencies;
    }

    public Set<DeclaresRelationship> getDependencRelationships() {
        return dependencyRelationships;
    }
}
