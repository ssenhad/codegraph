package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.server.api.resources.DeclaredDependency;
import com.dnfeitosa.codegraph.server.api.resources.DependencyResource;
import com.dnfeitosa.codegraph.server.api.resources.VersionResource;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;

@Component
public class ArtifactResourceConverter {

    private TypeResourceConverter typeResourceConverter;

    public ArtifactResourceConverter() {
        this.typeResourceConverter = new TypeResourceConverter();
    }

    public Artifact toModel(ArtifactResource resource) {
        String name = resource.getName();
        String organization = resource.getOrganization();
        Version version = new Version(resource.getVersion());

        Artifact artifact = new Artifact(name, organization, version);
        resource.getDependencies().forEach(dependency -> artifact.addDependency(toModel(dependency)));
        return artifact;
    }

    private Dependency toModel(DeclaredDependency dependency) {
        Version version = new Version(dependency.getVersion());
        return new Dependency(dependency.getOrganization(), dependency.getName(), version, emptySet());
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
        return new DeclaredDependency(dependency.getOrganization(), dependency.getName(), dependency.getVersion().getNumber(), dependency.getConfigurations());
    }

    private DependencyResource toDependencyResource(Artifact dependency) {
        DependencyResource resource = new DependencyResource();
        resource.setName(dependency.getName());
        resource.setOrganization(dependency.getOrganization());
        String number = dependency.getVersion().getNumber();
        resource.setVersion(new VersionResource(number, number));
        return resource;
    }

    public ArtifactsResource toResources(List<Artifact> artifacts) {
        List<ArtifactResource> artifactsResource = artifacts.stream()
                .map(this::toResource)
                .collect(toList());
        return new ArtifactsResource(artifactsResource);
    }
}
