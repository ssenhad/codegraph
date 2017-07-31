package com.dnfeitosa.codegraph.server.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.converters.ArtifactNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.db.repositories.DependencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
            .map(version -> {
                return new AvailableVersion(version, availability(version, dependenciesOnly, artifactsOnly));
            }).collect(toSet());
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
