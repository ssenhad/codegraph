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
package co.degraph.server.services;

import co.degraph.core.models.Artifact;
import co.degraph.core.models.AvailableVersion;
import co.degraph.core.models.Graph;
import co.degraph.core.models.Version;
import co.degraph.db.models.ArtifactNode;
import co.degraph.db.models.converters.ArtifactNodeConverter;
import co.degraph.db.models.relationships.DeclaresRelationship;
import co.degraph.db.repositories.ArtifactRepository;
import co.degraph.coollections.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service
public class ArtifactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactService.class);

    private final ArtifactNodeConverter nodeConverter;
    private final ArtifactRepository artifactRepository;

    @Autowired
    public ArtifactService(ArtifactNodeConverter nodeConverter, ArtifactRepository artifactRepository) {
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
            .map(Version::new)
            .filter(version -> !version.isDynamic())
            .map(AvailableVersion::new)
            .collect(toSet());
    }

    public Set<Artifact> getArtifactsFromOrganization(String organization) {
        Set<ArtifactNode> artifactNodes = artifactRepository.getArtifactsFromOrganization(organization);
        return toModel(artifactNodes);
    }

    public void saveDependencies(Set<Artifact> dependencyArtifacts) {
        Set<ArtifactNode> nodes = allArtifacts(dependencyArtifacts)
            .map(nodeConverter::toNode)
            .collect(toSet());

//        Set<String> ids = nodes.stream().map(ArtifactNode::getId).collect(toSet());
//        Map<String, ArtifactNode> byId = nodes.stream().collect(Collectors.toMap(n -> n.getId(), n -> n));
//
//        Collection<ArtifactNode> existing = artifactRepository.loadAll(ids);
//        existing.forEach(n -> {
//            if (byId.containsKey(n.getId())) {
//                byId.remove(n.getId());
//            }
//        });
//
//        LOGGER.info("Number of artifacts to be saved: {}. Will save only {}", nodes.size(), byId.size());
        artifactRepository.saveRelationshipsWithoutProperties(nodes);
    }

    private Stream<Artifact> allArtifacts(Set<Artifact> dependencyArtifacts) {
        /*
        */
        return Stream.concat(
            dependencyArtifacts.stream(),
            dependencyArtifacts.stream().flatMap(a -> a.getDependencies().stream()).map(d -> d.getArtifact())
        );
//        return dependencyArtifacts.stream().flatMap(a -> a.getDependencies().stream()).map(d -> d.getArtifact());
    }

    public Graph<Artifact, DependencyEdge> loadDependencyGraph(String organization, String name, String version) {
        Artifact root = load(organization, name, version);
        if (root == null) {
            return null;
        }

        Set<ArtifactNode> nodes = groupArtifacts(organization, name, version);
        Set<Artifact> artifacts = toModel(nodes);

        Set<DependencyEdge> edges = artifacts.stream()
            .flatMap(a -> a.getDependencies().stream())
            .map(DependencyEdge::new)
            .collect(toSet());
        return new Graph<>(root, collectNodes(artifacts), edges);
    }

    private Set<Artifact> toModel(Set<ArtifactNode> artifactNodes) {
        return artifactNodes.stream()
            .map(nodeConverter::toModel)
            .collect(toSet());
    }

    private Set<ArtifactNode> toNodes(Set<Artifact> dependencyArtifacts) {
        return dependencyArtifacts.stream()
            .map(nodeConverter::toNode)
            .collect(toSet());
    }

    private Set<Artifact> collectNodes(Set<Artifact> artifacts) {
        return Streams.concat(
            artifacts.stream(),
            artifacts.stream().flatMap(a -> a.getDependencies().stream().map(d -> d.getArtifact()))
        ).collect(toSet());

    }

    private Set<ArtifactNode> groupArtifacts(String organization, String name, String version) {
        Map<String, ArtifactNode> nodes = new HashMap<>();
        Set<DeclaresRelationship> declaresRelationships = artifactRepository.loadDependencyGraph(organization, name, version);
        declaresRelationships.stream()
            .forEach(relationship -> {
                ArtifactNode artifact = relationship.getArtifact();
                String artifactId = artifact.getId();
                if (!nodes.containsKey(artifactId)) {
                    nodes.put(artifactId, artifact);
                }
                nodes.get(artifactId).addDependency(relationship.getDependency(), relationship.getConfigurations());
            });
        return new HashSet<>(nodes.values());
    }
}
