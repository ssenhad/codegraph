package co.degraph.server.acceptance.api.controllers;

import co.degraph.core.models.Artifact;
import co.degraph.core.models.Version;
import co.degraph.server.acceptance.AcceptanceTestBase;
import co.degraph.server.api.controllers.ArtifactController;
import co.degraph.server.api.resources.ArtifactResource;
import co.degraph.server.api.resources.ArtifactVersions;
import co.degraph.server.api.resources.AvailableVersionResource;
import co.degraph.server.services.ArtifactService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static co.degraph.coollections.Coollections.asSet;
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
        Artifact artifact = new Artifact("co.degraph", "codegraph-server", new Version("1.0"))
            .addDependency(new Artifact("junit", "junit", new Version("4.12")), asSet("test"))
            .addDependency(new Artifact("co.degraph", "codegraph-core", new Version("1.0")), asSet("compile"));
        service.save(artifact);

        ResponseEntity<ArtifactResource> response = controller.getArtifact("co.degraph", "codegraph-server", "1.0");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        ArtifactResource artifactResource = response.getBody();
        check(artifactResource)
            .is("co.degraph", "codegraph-server", "1.0")
            .hasDependencies(2)
            .hasDependency("co.degraph", "codegraph-core", "1.0", asSet("compile"))
            .hasDependency("junit", "junit", "4.12", asSet("test"));
    }

    @Test
    public void shouldReturnA404ResponseWhenAnArtifactIsNotFound() {
        ResponseEntity<ArtifactResource> response = controller.getArtifact("something", "not", "existing");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void shouldReturnAllVersionsOfAnArtifactAndDeclaredDependency() {
        service.save(new Artifact("co.degraph", "codegraph-core", new Version("1.0")));
        service.save(new Artifact("co.degraph", "codegraph-server", new Version("1.0")) {{
            addDependency(new Artifact("co.degraph", "codegraph-core", new Version("1.1")), asSet("compile"));
        }});
        service.save(new Artifact("co.degraph", "codegraph-server", new Version("1.2")) {{
            addDependency(new Artifact("co.degraph", "codegraph-core", new Version("1.+")), asSet("compile"));
        }});

        ResponseEntity<ArtifactVersions> response = controller.getVersions("co.degraph", "codegraph-core");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ArtifactVersions artifactVersions = response.getBody();
        assertThat(artifactVersions.getArtifact().getOrganization(), is("co.degraph"));
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
