package com.dnfeitosa.codegraph.api.converters;

import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import com.dnfeitosa.codegraph.core.models.Project;
import org.junit.Before;
import org.junit.Test;

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
}