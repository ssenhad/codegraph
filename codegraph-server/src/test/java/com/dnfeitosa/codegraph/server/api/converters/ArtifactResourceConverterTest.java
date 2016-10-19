package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;
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

        ArtifactResource dependencyResource = new ArtifactResource();
        dependencyResource.setName("dependency-name");
        dependencyResource.setType("dependency-type");
        dependencyResource.setOrganization("dependency-organization");
        dependencyResource.setExtension("dependency-extension");
        dependencyResource.setVersion("dependency-version");
        resource.addDependency(dependencyResource);

        Artifact artifact = converter.toModel(resource);

        assertThat(artifact.getId(), is(id));
        assertThat(artifact.getName(), is(name));
        assertThat(artifact.getOrganization(), is(organization));
        assertThat(artifact.getType(), is(type));
        assertThat(artifact.getExtension(), is(extension));
        assertThat(artifact.getVersion().getNumber(), is(version));

        List<Artifact> dependencies = artifact.getDependencies();
        assertThat(dependencies.size(), is(1));
        Artifact dependency = dependencies.get(0);
        assertThat(dependency.getName(), is("dependency-name"));
        assertThat(dependency.getType(), is("dependency-type"));
        assertThat(dependency.getOrganization(), is("dependency-organization"));
        assertThat(dependency.getVersion().getNumber(), is("dependency-version"));
        assertThat(dependency.getExtension(), is("dependency-extension"));
    }

    @Test
    public void shouldConvertAnArtifactModelToArtifactResource() {
        Artifact artifact = new Artifact(id, name, organization, new Version(version), type, extension);
        Artifact dependency = new Artifact("dependency-name", "dependency-organization", new Version("dependency-version"), "dependency-type", "dependency-extension");
        artifact.addDependency(dependency);

        ArtifactResource resource = converter.toResource(artifact);

        assertThat(resource.getId(), is(id));
        assertThat(resource.getName(), is(name));
        assertThat(resource.getType(), is(type));
        assertThat(resource.getExtension(), is(extension));
        assertThat(resource.getOrganization(), is(organization));
        assertThat(resource.getVersion(), is(version));

        assertThat(resource.getDependencies().size(), is(1));

        ArtifactResource dependencyResource = resource.getDependencies().get(0);
        assertThat(dependencyResource.getName(), is("dependency-name"));
        assertThat(dependencyResource.getOrganization(), is("dependency-organization"));
        assertThat(dependencyResource.getExtension(), is("dependency-extension"));
        assertThat(dependencyResource.getVersion(), is("dependency-version"));
        assertThat(dependencyResource.getType(), is("dependency-type"));
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

    @Test
    public void shouldConvertAnArtifactWithItsTypes() {
        ArtifactResource resource = new ArtifactResource();
        resource.setName("artifact");

        TypeResource typeResource = new TypeResource();
        typeResource.setName("ArtifactResourceConverterTest");
        typeResource.setPackageName("com.dnfeitosa.codegraph.server.api.converters");
        typeResource.setUsage("test");
        typeResource.setType("class");

        resource.addType(typeResource);

        Artifact artifact = converter.toModel(resource);

        assertThat(artifact.getName(), is("artifact"));

        Set<Type> types = artifact.getTypes();
        assertThat(types.size(), is(1));

        Type type = $(types).first();
        assertThat(type.getName(), is("ArtifactResourceConverterTest"));
        assertThat(type.getPackageName(), is("com.dnfeitosa.codegraph.server.api.converters"));
        assertThat(type.getUsage(), is("test"));
        assertThat(type.getType(), is("class"));
    }
}