package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectNodeConverterTest {

    private final Long id = 10L;
    private final String name = "project-name";
    private final String organization = "project-organization";
    private final String version = "project-version";

    private ProjectNodeConverter converter;

    @Before
    public void setUp() {
        converter = new ProjectNodeConverter();
    }

    @Test
    public void shouldConvertAProjectToNode() {
        Project project = new Project(id, name, organization, new Version(version));
        project.addArtifact(new Artifact("artifact-name", "artifact-type", "artifact-extension", new Version("artifact-version")));

        ProjectNode node = converter.toNode(project);

        assertThat(node.getId(), is(id));
        assertThat(node.getName(), is(name));
        assertThat(node.getOrganization(), is(organization));
        assertThat(node.getVersion(), is(version));

        assertThat(node.getArtifacts().size(), is(1));

        ArtifactNode artifactNode = node.getArtifacts().stream().findFirst().get();
        assertThat(artifactNode.getName(), is("artifact-name"));
        assertThat(artifactNode.getType(), is("artifact-type"));
        assertThat(artifactNode.getExtension(), is("artifact-extension"));
        assertThat(artifactNode.getVersion(), is("artifact-version"));
    }

    @Test
    public void shouldConvertAProjectNodeToModel() {
        ProjectNode node = new ProjectNode(id, name, organization, version);
        ArtifactNode artifactNode = new ArtifactNode();
        artifactNode.setName("artifact-name");
        artifactNode.setExtension("artifact-extension");
        artifactNode.setType("artifact-type");
        artifactNode.setVersion("artifact-version");
        node.addArtifact(artifactNode);

        Project project = converter.toModel(node);

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
}
