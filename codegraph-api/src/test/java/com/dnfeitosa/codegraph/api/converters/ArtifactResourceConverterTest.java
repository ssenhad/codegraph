package com.dnfeitosa.codegraph.api.converters;

import com.dnfeitosa.codegraph.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Version;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactResourceConverterTest {

    private final Long id = 10L;
    private final String name = "extension-name";
    private final String organization = "extension-organization";
    private final String version = "extension-version";
    private final String type = "extension-type";
    private final String extension = "extension-extension";

    private ArtifactResourceConverter converter;

    @Before
    public void setUp() {
        converter = new ArtifactResourceConverter();
    }

    @Test
    public void shouldConvertAnArtifactResourceToArtifactModel() {
        ArtifactResource resource = new ArtifactResource();
        resource.setId(id);
        resource.setName(name);
        resource.setOrganization(organization);
        resource.setVersion(version);
        resource.setType(type);
        resource.setExtension(extension);

        Artifact artifact = converter.toModel(resource);

        assertThat(artifact.getId(), is(id));
        assertThat(artifact.getName(), is(name));
        assertThat(artifact.getOrganization(), is(organization));
        assertThat(artifact.getType(), is(type));
        assertThat(artifact.getExtension(), is(extension));
        assertThat(artifact.getVersion().getNumber(), is(version));
    }

    @Test
    public void shouldConvertAnArtifactModelToArtifactResource() {
        Artifact artifact = new Artifact(id, name, organization, new Version(version), type, extension);

        ArtifactResource resource = converter.toResource(artifact);

        assertThat(resource.getId(), is(id));
        assertThat(resource.getName(), is(name));
        assertThat(resource.getType(), is(type));
        assertThat(resource.getExtension(), is(extension));
        assertThat(resource.getOrganization(), is(organization));
        assertThat(resource.getVersion(), is(version));
    }

    @Test
    public void shouldConvertAListOfArtifactsToResource() {
        ArtifactsResource resources = converter.toResources(asList(
                new Artifact(10L, null, null, new Version(null), null, null),
                new Artifact(11L, null, null, new Version(null), null, null)));

        List<ArtifactResource> artifacts = resources.getArtifacts();

        artifacts.sort(comparing(ArtifactResource::getId));

        assertThat(artifacts.size(), is(2));
        assertThat(artifacts.get(0).getId(), is(10L));
        assertThat(artifacts.get(1).getId(), is(11L));
    }
}