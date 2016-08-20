package com.dnfeitosa.codegraph.api.converters;

import com.dnfeitosa.codegraph.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import com.dnfeitosa.codegraph.api.resources.ProjectResources;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.core.models.Version;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
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

        ArtifactResource artifactResource = new ArtifactResource();
        artifactResource.setVersion("artifact-version");
        artifactResource.setName("artifact-name");
        artifactResource.setExtension("artifact-extension");
        artifactResource.setType("artifact-type");
        resource.setArtifacts(asList(artifactResource));

        Project project = converter.toModel(resource);

        assertThat(project.getId(), is(id));
        assertThat(project.getName(), is(name));
        assertThat(project.getOrganization(), is(organization));
        assertThat(project.getVersion().getNumber(), is(version));

        assertThat(project.getArtifacts().size(), is(1));
        Artifact artifact = project.getArtifacts().get(0);

        assertThat(artifact.getName(), is("artifact-name"));
        assertThat(artifact.getExtension(), is("artifact-extension"));
        assertThat(artifact.getType(), is("artifact-type"));
        assertThat(artifact.getVersion().getNumber(), is("artifact-version"));
    }

    @Test
    public void shouldConvertAProjectModelToProjectResource() {
        Project project = new Project(id, name, organization, new Version(version));
        project.addArtifact(new Artifact("artifact-name", "artifact-type", "artifact-extension", new Version("artifact-version")));

        ProjectResource resource = converter.toResource(project);

        assertThat(resource.getId(), is(id));
        assertThat(resource.getName(), is(name));
        assertThat(resource.getOrganization(), is(organization));
        assertThat(resource.getVersion(), is(version));

        assertThat(resource.getArtifacts().size(), is(1));

        ArtifactResource artifactResource = resource.getArtifacts().get(0);
        assertThat(artifactResource.getName(), is("artifact-name"));
        assertThat(artifactResource.getExtension(), is("artifact-extension"));
        assertThat(artifactResource.getType(), is("artifact-type"));
        assertThat(artifactResource.getVersion(), is("artifact-version"));
    }

    @Test
    public void shouldConvertAListOfProjectsToResource() {
        ProjectResources resources = converter.toResources(asList(
                new Project(10L, null, null, new Version(null)),
                new Project(11L, null, null, new Version(null))));

        List<ProjectResource> projects = resources.getProjects();

        projects.sort(comparing(ProjectResource::getId));

        assertThat(projects.size(), is(2));
        assertThat(projects.get(0).getId(), is(10L));
        assertThat(projects.get(1).getId(), is(11L));
    }
}