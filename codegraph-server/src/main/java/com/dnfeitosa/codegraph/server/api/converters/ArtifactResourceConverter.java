package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.server.api.resources.DependencyResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import com.dnfeitosa.codegraph.server.api.resources.VersionResource;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ArtifactResourceConverter {

    private TypeResourceConverter typeResourceConverter;

    public ArtifactResourceConverter() {
        this.typeResourceConverter = new TypeResourceConverter();
    }

    public Artifact toModel(ArtifactResource resource) {
        Long id = resource.getId();
        String name = resource.getName();
        String organization = resource.getOrganization();
        Version version = new Version(resource.getVersion());
        String artifactType = resource.getType();
        String extension = resource.getExtension();

        Artifact artifact = new Artifact(id, name, organization, version, artifactType, extension);
        resource.getDependencies().forEach(dependency -> artifact.addDependency(toModel(dependency)));
        resource.getTypes().forEach(type -> artifact.addType(toModel(type)));
        return artifact;
    }

    private Artifact toModel(DependencyResource dependency) {
        Version version = new Version(dependency.getVersion().getDeclared());
        return new Artifact(dependency.getName(), dependency.getOrganization(), version, null, null);
    }

    private Type toModel(TypeResource type) {
        return typeResourceConverter.toModel(type);
    }

    public ArtifactResource toResource(Artifact artifact) {
        ArtifactResource resource = new ArtifactResource();
        resource.setId(artifact.getId());
        resource.setName(artifact.getName());
        resource.setOrganization(artifact.getOrganization());
        resource.setVersion(artifact.getVersion().getNumber());
        resource.setExtension(artifact.getExtension());
        resource.setType(artifact.getType());

        artifact.getDependencies()
                .forEach(dependency -> resource.addDependency(toDependencyResource(dependency)));
        return resource;
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
