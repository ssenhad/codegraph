package com.dnfeitosa.codegraph.server.acceptance.api.controllers;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.acceptance.AcceptanceTestBase;
import com.dnfeitosa.codegraph.server.api.controllers.ArtifactController;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactVersions;
import com.dnfeitosa.codegraph.server.api.resources.AvailableVersionResource;
import com.dnfeitosa.codegraph.server.api.resources.DeclaredDependency;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.codegraph.core.utils.Arrays.asSet;
import static org.apache.commons.collections4.IterableUtils.find;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArtifactControllerTest extends AcceptanceTestBase {

    @Autowired
    private ArtifactController controller;

    @Autowired
    private ArtifactService service;

    @Test
    public void shouldReturnAnArtifactResourceWithItsDependencies() {
        Artifact artifact = new Artifact("com.dnfeitosa.codegraph", "codegraph-server", new Version("1.0"));
        artifact.addDependency(new Dependency(new Artifact("org.junit", "junit", new Version("4.12")), asSet("test")));
        artifact.addDependency(new Dependency(new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.0")), asSet("compile")));
        service.save(artifact);

        ResponseEntity<ArtifactResource> response = controller.getArtifact("com.dnfeitosa.codegraph", "codegraph-server", "1.0");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        ArtifactResource artifactResource = response.getBody();
        assertThat(artifactResource.getOrganization(), is("com.dnfeitosa.codegraph"));
        assertThat(artifactResource.getName(), is("codegraph-server"));
        assertThat(artifactResource.getVersion(), is("1.0"));

        List<DeclaredDependency> dependencies = artifactResource.getDependencies();
        assertThat(dependencies.size(), is(2));

        DeclaredDependency junit = find(dependencies, d -> "junit".equals(d.getName()));
        assertThat(junit.getOrganization(), is("org.junit"));
        assertThat(junit.getName(), is("junit"));
        assertThat(junit.getVersion(), is("4.12"));
        assertThat(junit.getConfigurations(), is(asSet("test")));

        DeclaredDependency codegraphCore = find(dependencies, d -> "codegraph-core".equals(d.getName()));
        assertThat(codegraphCore.getOrganization(), is("com.dnfeitosa.codegraph"));
        assertThat(codegraphCore.getName(), is("codegraph-core"));
        assertThat(codegraphCore.getVersion(), is("1.0"));
        assertThat(codegraphCore.getConfigurations(), is(asSet("compile")));
    }

    @Test
    public void shouldReturnA404ResponseWhenAnArtifactIsNotFound() {
        ResponseEntity<ArtifactResource> response = controller.getArtifact("something", "not", "existing");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void shouldReturnAllVersionsOfAnArtifactAndDeclaredDependency() {
        service.save(new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.0")));
        service.save(new Artifact("com.dnfeitosa.codegraph", "codegraph-server", new Version("1.0")) {{
            addDependency(new Dependency(new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.1")), asSet("compile")));
        }});
        service.save(new Artifact("com.dnfeitosa.codegraph", "codegraph-server", new Version("1.2")) {{
            addDependency(new Dependency(new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.+")), asSet("compile")));
        }});

        ResponseEntity<ArtifactVersions> response = controller.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ArtifactVersions artifactVersions = response.getBody();
        assertThat(artifactVersions.getArtifact().getOrganization(), is("com.dnfeitosa.codegraph"));
        assertThat(artifactVersions.getArtifact().getName(), is("codegraph-core"));
        assertNull(artifactVersions.getArtifact().getVersion());
        assertTrue(artifactVersions.getArtifact().getDependencies().isEmpty());

        Set<AvailableVersionResource> versions = artifactVersions.getVersions();
        assertThat(versions.size(), is(2));
        {
            AvailableVersionResource versionResource = find(versions, byVersion("1.0"));
            assertThat(versionResource.getVersion(), is("1.0"));
        }{
            AvailableVersionResource versionResource = find(versions, byVersion("1.1"));
            assertThat(versionResource.getVersion(), is("1.1"));
        }
    }

    private org.apache.commons.collections4.Predicate<AvailableVersionResource> byVersion(String x) {
        return v -> v.getVersion().equals(x);
    }
}
