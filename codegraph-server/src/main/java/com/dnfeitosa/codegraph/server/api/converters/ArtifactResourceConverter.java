package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactVersions;
import com.dnfeitosa.codegraph.server.api.resources.AvailableVersionResource;
import com.dnfeitosa.codegraph.server.api.resources.DeclaredDependency;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class ArtifactResourceConverter {

    public Artifact toModel(ArtifactResource resource) {
        String name = resource.getName();
        String organization = resource.getOrganization();
        Version version = new Version(resource.getVersion());

        Artifact artifact = new Artifact(organization, name, version);
        resource.getDependencies().forEach(dependency -> artifact.addDependency(toModel(dependency)));
        return artifact;
    }

    private Dependency toModel(DeclaredDependency dependency) {
        Version version = new Version(dependency.getVersion());
        return new Dependency(dependency.getOrganization(), dependency.getName(), version, dependency.getConfigurations());
    }

    public ArtifactResource toResource(Artifact artifact) {
        List<DeclaredDependency> dependencies = artifact.getDependencies()
            .stream()
            .map(this::toResource)
            .collect(toList());

        return new ArtifactResource(
            artifact.getOrganization(),
            artifact.getName(),
            artifact.getVersion().getNumber(),
            dependencies);
    }

    private DeclaredDependency toResource(Dependency dependency) {
        String organization = dependency.getOrganization();
        String name = dependency.getName();
        String version = dependency.getVersion().getNumber();
        Set<String> configurations = dependency.getConfigurations();
        return new DeclaredDependency(organization, name, version, configurations);
    }

    public ArtifactVersions toResource(String organization, String name, Set<AvailableVersion> versions) {
        ArtifactResource artifactResource = new ArtifactResource(organization, name, null, Collections.emptyList());
        Set<AvailableVersionResource> availableVersion = versions.stream()
            .map(av -> toResource(av))
            .collect(toSet());
        return new ArtifactVersions(artifactResource, availableVersion);
    }

    private AvailableVersionResource toResource(AvailableVersion version) {
        Set<String> availability = asList(version.getAvailability()).stream()
            .map(AvailableVersion.Availability::toString)
            .collect(toSet());
        return new AvailableVersionResource(version.getVersion().getNumber(), availability);
    }
}
