package com.dnfeitosa.codegraph.api.converters;

import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import com.dnfeitosa.codegraph.api.resources.ProjectResources;
import com.dnfeitosa.codegraph.core.models.Project;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProjectResourceConverter {

    public Project toModel(ProjectResource resource) {
        return new Project(resource.getId(), resource.getName(), resource.getOrganization(), resource.getVersion());
    }

    public ProjectResource toResource(Project project) {
        ProjectResource resource = new ProjectResource();
        resource.setId(project.getId());
        resource.setName(project.getName());
        resource.setOrganization(project.getOrganization());
        resource.setVersion(project.getVersion());
        return resource;
    }

    public ProjectResources toResources(List<Project> projects) {
        List<ProjectResource> projectResources = projects.stream()
                .map(this::toResource)
                .collect(toList());
        return new ProjectResources(projectResources);
    }
}
