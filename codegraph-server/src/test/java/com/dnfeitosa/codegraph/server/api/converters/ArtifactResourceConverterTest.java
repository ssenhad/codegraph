package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Artifacts;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactVersions;
import com.dnfeitosa.codegraph.server.api.resources.AvailableVersionResource;
import com.dnfeitosa.codegraph.server.api.resources.DependencyResource;
import org.apache.commons.collections4.Predicate;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;
import static com.dnfeitosa.coollections.Coollections.asSet;
import static java.util.Arrays.asList;
import static org.apache.commons.collections4.IterableUtils.find;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArtifactResourceConverterTest {

    private final String name = "extension-name";
    private final String organization = "extension-organization";
    private final String version = "extension-version";

    private ArtifactResourceConverter converter;
    private Artifacts artifacts;

    @Before
    public void setUp() {
        artifacts = new Artifacts();
        converter = new ArtifactResourceConverter(artifacts);
    }

    @Test
    public void shouldConvertAnArtifactResourceToArtifactModel() {
        ArtifactResource resource = new ArtifactResource(organization, name, version, asList(
            new DependencyResource("dependency-organization", "dependency-name", "dependency-version", asSet("compile", "test")))
        );

        Artifact artifact = converter.toModel(resource);

        assertThat(artifact.getName(), is(name));
        assertThat(artifact.getOrganization(), is(organization));
        assertThat(artifact.getVersion().getNumber(), is(version));

        Set<com.dnfeitosa.codegraph.core.models.Dependency> dependencies = artifact.getDependencies();
        assertThat(dependencies.size(), is(1));
        com.dnfeitosa.codegraph.core.models.Dependency dependency = $(dependencies).first();
        assertThat(dependency.getName(), is("dependency-name"));
        assertThat(dependency.getOrganization(), is("dependency-organization"));
        assertThat(dependency.getVersion().getNumber(), is("dependency-version"));
        assertThat(dependency.getConfigurations(), hasItems("compile", "test"));
    }

    @Test
    public void shouldConvertAnArtifactModelToArtifactResource() {
        Artifact artifact = artifacts.artifact(organization, name, new Version(version));
        artifact.addDependency(artifacts.artifact("dependency-organization", "dependency-name", new Version("dependency-version")), asSet("compile"));

        ArtifactResource resource = converter.toResource(artifact);

        assertThat(resource.getName(), is(name));
        assertThat(resource.getOrganization(), is(organization));
        assertThat(resource.getVersion(), is(version));

        assertThat(resource.getDependencies().size(), is(1));

        DependencyResource dependencyResource = resource.getDependencies().get(0);
        assertThat(dependencyResource.getName(), is("dependency-name"));
        assertThat(dependencyResource.getOrganization(), is("dependency-organization"));
        assertThat(dependencyResource.getVersion(), is("dependency-version"));
    }

    @Test
    public void shouldConvertTheAvailableVersionsDataToResource() {
        ArtifactVersions resource = converter.toResource("com.dnfeitosa.codegraph", "codegraph-core", asSet(
            new AvailableVersion(new Version("1.0")),
            new AvailableVersion(new Version("1.1"))
        ));

        ArtifactResource artifact = resource.getArtifact();
        assertThat(artifact.getOrganization(), is("com.dnfeitosa.codegraph"));
        assertThat(artifact.getName(), is("codegraph-core"));
        assertTrue(artifact.getDependencies().isEmpty());
        assertNull(artifact.getVersion());

        Set<AvailableVersionResource> versions = resource.getVersions();
        assertThat(versions.size(), is(2));

        {
            AvailableVersionResource version = find(versions, byVersion("1.0"));
            assertNotNull(version);
        }{
            AvailableVersionResource version = find(versions, byVersion("1.1"));
            assertNotNull(version);
        }
    }

    private Predicate<AvailableVersionResource> byVersion(String x) {
        return v -> v.getVersion().equals(x);
    }
}
