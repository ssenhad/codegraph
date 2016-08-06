package com.dnfeitosa.codegraph.api.converters;

import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import com.dnfeitosa.codegraph.api.resources.ProjectResources;
import com.dnfeitosa.codegraph.core.models.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectResourceConverterTest {

    private final Long id = 10L;
    private final String name = "project-name";
    private final String organization = "project-organization";
    private final String version = "project-version";

    private ProjectResourceConverter converter;

    @Before
    public void setUp() {
        converter = new ProjectResourceConverter();
    }

    @Test
    public void shouldConvertAProjectResourceToProjectModel() {
        ProjectResource resource = new ProjectResource();
        resource.setId(id);
        resource.setName(name);
        resource.setOrganization(organization);
        resource.setVersion(version);

        Project project = converter.toModel(resource);

        assertThat(project.getId(), is(id));
        assertThat(project.getName(), is(name));
        assertThat(project.getOrganization(), is(organization));
        assertThat(project.getVersion(), is(version));
    }

    @Test
    public void shouldConvertAProjectModelToProjectResource() {
        Project project = new Project(id, name, organization, version);

        ProjectResource resource = converter.toResource(project);

        assertThat(resource.getId(), is(id));
        assertThat(resource.getName(), is(name));
        assertThat(resource.getOrganization(), is(organization));
        assertThat(resource.getVersion(), is(version));
    }

    @Test
    public void shouldConvertAListOfProjectsToResource() {
        ProjectResources resources = converter.toResources(asList(
                new Project(10L, null, null, null),
                new Project(11L, null, null, null)));

        List<ProjectResource> projects = resources.getProjects();

        projects.sort(Comparator.comparing(ProjectResource::getId));

        assertThat(projects.size(), is(2));
        assertThat(projects.get(0).getId(), is(10L));
        assertThat(projects.get(1).getId(), is(11L));
    }
}