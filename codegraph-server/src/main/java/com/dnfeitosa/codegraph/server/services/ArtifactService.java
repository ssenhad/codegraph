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
import com.dnfeitosa.codegraph.db.models.DependencyNode;
import com.dnfeitosa.codegraph.db.models.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.db.repositories.DependencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.dnfeitosa.codegraph.core.models.AvailableVersion.Availability.ARTIFACT;
import static com.dnfeitosa.codegraph.core.models.AvailableVersion.Availability.DEPENDENCY;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.collate;
import static org.apache.commons.collections4.CollectionUtils.subtract;

@Service
public class ArtifactService {

    private final ArtifactNodeConverter nodeConverter;
    private final ArtifactRepository artifactRepository;
    private final DependencyRepository dependencyRepository;

    @Autowired
    public ArtifactService(ArtifactNodeConverter nodeConverter,
                           ArtifactRepository artifactRepository,
                           DependencyRepository dependencyRepository) {
        this.nodeConverter = nodeConverter;
        this.artifactRepository = artifactRepository;
        this.dependencyRepository = dependencyRepository;
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
        Set<String> dependencyVersions = dependencyRepository.getVersions(organization, name);
        Set<String> artifactVersions = artifactRepository.getVersions(organization, name);

        Collection<String> dependenciesOnly = subtract(dependencyVersions, artifactVersions);
        Collection<String> artifactsOnly = subtract(artifactVersions, dependencyVersions);
        return collate(dependencyVersions, artifactVersions, false).stream()
            .map(version -> new Version(version))
            .filter(version -> !version.isDynamic())
            .map(version -> toAvailableVersion(dependenciesOnly, artifactsOnly, version))
            .collect(toSet());
    }

    public Set<Artifact> getArtifactsFromOrganization(String organization) {
        Set<ArtifactNode> artifactNodes = artifactRepository.getArtifactsFromOrganization(organization);
        Set<DependencyNode> dependencyArtifactNodes = dependencyRepository.getArtifactsFromOrganization(organization);
        Iterable<Artifact> artifacts = artifactNodes.stream()
            .map(nodeConverter::toModel)
            .collect(toSet());

        Iterable<Artifact> dependencyArtifacts = dependencyArtifactNodes.stream()
            .map(node -> new Artifact(node.getOrganization(), node.getName(), new Version(node.getVersion())))
            .collect(toSet());
        return new HashSet(collate(artifacts, dependencyArtifacts, false));
    }

    private AvailableVersion toAvailableVersion(Collection<String> dependenciesOnly, Collection<String> artifactsOnly, Version version) {
        return new AvailableVersion(version, availability(version, dependenciesOnly, artifactsOnly));
    }

    private AvailableVersion.Availability[] availability(Version version, Collection<String> dependenciesOnly, Collection<String> artifactsOnly) {
        if (dependenciesOnly.contains(version.getNumber())) {
            return new AvailableVersion.Availability[] { DEPENDENCY };
        }
        if (artifactsOnly.contains(version.getNumber())) {
            return new AvailableVersion.Availability[] { ARTIFACT };
        }
        return new AvailableVersion.Availability[] { ARTIFACT, DEPENDENCY };
    }
}
