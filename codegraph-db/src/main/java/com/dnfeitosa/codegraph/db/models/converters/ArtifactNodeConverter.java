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
package com.dnfeitosa.codegraph.db.models.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Artifacts;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtifactNodeConverter {

    private final Artifacts artifacts;

    @Autowired
    public ArtifactNodeConverter(Artifacts artifacts) {
        this.artifacts = artifacts;
    }

    public ArtifactNode toNode(Artifact artifact) {
        String organization = artifact.getOrganization();
        String name = artifact.getName();
        String number = artifact.getVersion().getNumber();

        ArtifactNode artifactNode = new ArtifactNode(organization, name, number);
        artifact.getDependencies().forEach(dependency -> {
            artifactNode.addDependency(toNode(dependency.getArtifact()), dependency.getConfigurations());
        });

        return artifactNode;
    }

    public Artifact toModel(ArtifactNode node) {
        Artifact artifact = artifacts.artifact(node.getOrganization(), node.getName(), new Version(node.getVersion()));
        node.getDeclaredDependencies().forEach(declaredDependency -> {
            Dependency dependency = toModel(declaredDependency);
            artifact.addDependency(dependency);
        });
        return artifact;
    }

    private Dependency toModel(DeclaresRelationship declared) {
        ArtifactNode dependency = declared.getDependency();
        String organization = dependency.getOrganization();
        String name = dependency.getName();
        String version = dependency.getVersion();
        Artifact artifact = artifacts.artifact(organization, name, new Version(version));
        return new Dependency(artifact, declared.getConfigurations());
    }
}
