package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Project;
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
        Project project = new Project(id, name, organization, version);

        ProjectNode node = converter.toNode(project);

        assertThat(node.getId(), is(id));
        assertThat(node.getName(), is(name));
        assertThat(node.getOrganization(), is(organization));
        assertThat(node.getVersion(), is(version));
    }

    @Test
    public void shouldConvertAProjectNodeToModel() {
        ProjectNode node = new ProjectNode(id, name, organization, version);

        Project project = converter.toModel(node);

        assertThat(project.getId(), is(id));
        assertThat(project.getName(), is(name));
        assertThat(project.getOrganization(), is(organization));
        assertThat(project.getVersion(), is(version));
    }
}
