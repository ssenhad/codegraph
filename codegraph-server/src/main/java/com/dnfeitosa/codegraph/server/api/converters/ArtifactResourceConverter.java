package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ArtifactResourceConverter {



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

    private Type toModel(TypeResource type) {
        return new Type(type.getName(), type.getPackageName(), type.getUsage(), type.getType());
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
                .forEach(dependency -> resource.addDependency(toResource(dependency)));
        return resource;
    }

    public ArtifactsResource toResources(List<Artifact> artifacts) {
        List<ArtifactResource> artifactsResource = artifacts.stream()
                .map(this::toResource)
                .collect(toList());
        return new ArtifactsResource(artifactsResource);
    }
}
