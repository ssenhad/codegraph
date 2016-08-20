package com.dnfeitosa.codegraph.api.converters;

import com.dnfeitosa.codegraph.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import com.dnfeitosa.codegraph.api.resources.ProjectResources;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.core.models.Version;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProjectResourceConverter {

    public Project toModel(ProjectResource resource) {
        Project project = new Project(resource.getId(), resource.getName(), resource.getOrganization(), new Version(resource.getVersion()));
        resource.getArtifacts().stream().forEach(artifactResource -> {
            project.addArtifact(toArtifact(artifactResource));
        });
        return project;
    }

    private Artifact toArtifact(ArtifactResource artifactResource) {
        String name = artifactResource.getName();
        String type = artifactResource.getType();
        String extension = artifactResource.getExtension();
        Version version = new Version(artifactResource.getVersion());
        return new Artifact(name, type, extension, version);
    }

    public ProjectResource toResource(Project project) {
        ProjectResource resource = new ProjectResource();
        resource.setId(project.getId());
        resource.setName(project.getName());
        resource.setOrganization(project.getOrganization());
        resource.setVersion(project.getVersion().getNumber());

        List<ArtifactResource> artifactResources = project.getArtifacts().stream()
                .map(this::toArtifactResource)
                .collect(toList());
        resource.setArtifacts(artifactResources);
        return resource;
    }

    private ArtifactResource toArtifactResource(Artifact artifact) {
        ArtifactResource resource = new ArtifactResource();
        resource.setName(artifact.getName());
        resource.setExtension(artifact.getExtension());
        resource.setVersion(artifact.getVersion().getNumber());
        resource.setType(artifact.getType());
        return resource;
    }

    public ProjectResources toResources(List<Project> projects) {
        List<ProjectResource> projectResources = projects.stream()
                .map(this::toResource)
                .collect(toList());
        return new ProjectResources(projectResources);
    }
}
