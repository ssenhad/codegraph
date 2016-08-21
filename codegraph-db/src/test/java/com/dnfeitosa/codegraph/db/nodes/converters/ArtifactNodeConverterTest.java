package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactNodeConverterTest {

    private final Long id = 10L;
    private final String name = "artifact-name";
    private final String organization = "artifact-organization";
    private final String version = "artifact-version";
    private final String type = "artifact-type";
    private final String extension = "artifact-extension";

    private ArtifactNodeConverter converter;

    @Before
    public void setUp() {
        converter = new ArtifactNodeConverter();
    }

    @Test
    public void shouldConvertAnArtifactToNode() {
        Artifact artifact = new Artifact(id, name, organization, new Version(version), type, extension);

        ArtifactNode node = converter.toNode(artifact);

        assertThat(node.getId(), is(id));
        assertThat(node.getName(), is(name));
        assertThat(node.getOrganization(), is(organization));
        assertThat(node.getVersion(), is(version));
    }

    @Test
    public void shouldConvertAnArtifactNodeToModel() {
        ArtifactNode node = new ArtifactNode(id, name, organization, version, type, extension);

        Artifact artifact = converter.toModel(node);

        assertThat(artifact.getId(), is(id));
        assertThat(artifact.getName(), is(name));
        assertThat(artifact.getOrganization(), is(organization));
        assertThat(artifact.getVersion().getNumber(), is(version));
    }
}
