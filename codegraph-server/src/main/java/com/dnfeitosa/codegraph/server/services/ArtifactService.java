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

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class ArtifactService {

    private final ArtifactNodeConverter nodeConverter;
    private final ArtifactRepository artifactRepository;

    @Autowired
    public ArtifactService(ArtifactNodeConverter nodeConverter,
                           ArtifactRepository artifactRepository) {
        this.nodeConverter = nodeConverter;
        this.artifactRepository = artifactRepository;
    }

    public Artifact load(String organization, String name, String version) {
        ArtifactNode artifactNode = artifactRepository.load(organization, name, version);
        if (artifactNode == null) {
            return null;
        }
        return nodeConverter.toModel(artifactNode);
    }

    public void save(Artifact artifact) {
        ArtifactNode artifactNode = nodeConverter.toNode(artifact);
        artifactRepository.save(artifactNode);
    }

    public Set<AvailableVersion> getVersions(String organization, String name) {
        Set<String> artifactVersions = artifactRepository.getVersions(organization, name);

        return artifactVersions.stream()
            .map(version -> new Version(version))
            .filter(version -> !version.isDynamic())
            .map(version -> new AvailableVersion(version))
            .collect(toSet());
    }

    public Set<Artifact> getArtifactsFromOrganization(String organization) {
        Set<ArtifactNode> artifactNodes = artifactRepository.getArtifactsFromOrganization(organization);
        return artifactNodes.stream()
            .map(nodeConverter::toModel)
            .collect(toSet());
    }

    public void save(Set<Artifact> dependencyArtifacts) {
        Set<ArtifactNode> artifacts = dependencyArtifacts.stream()
            .map(nodeConverter::toNode)
            .collect(toSet());
        artifactRepository.save(artifacts);
    }
}
