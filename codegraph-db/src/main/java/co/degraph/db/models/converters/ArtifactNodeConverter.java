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
package co.degraph.db.models.converters;

import co.degraph.core.models.Artifact;
import co.degraph.core.models.Artifacts;
import co.degraph.core.models.Version;
import co.degraph.db.models.ArtifactNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Provider;


@Component
public class ArtifactNodeConverter {

    private final Provider<Artifacts> artifacts;

    @Autowired
    public ArtifactNodeConverter(Provider<Artifacts> artifacts) {
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
        Artifact artifact = artifacts.get().artifact(node.getOrganization(), node.getName(), new Version(node.getVersion()));
        node.getDeclaredDependencies().forEach(declaredDependency -> {
            artifact.addDependency(toModel(declaredDependency.getDependency()), declaredDependency.getConfigurations());
        });
        return artifact;
    }
}
