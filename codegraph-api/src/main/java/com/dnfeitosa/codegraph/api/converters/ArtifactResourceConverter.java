package com.dnfeitosa.codegraph.api.converters;

import com.dnfeitosa.codegraph.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Version;
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
        String type = resource.getType();
        String extension = resource.getExtension();

        Artifact artifact = new Artifact(id, name, organization, version, type, extension);
        resource.getDependencies()
                .forEach(dependency -> artifact.addDependency(toModel(dependency)));
        return artifact;
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