package com.dnfeitosa.codegraph.components.server.services;

import com.dnfeitosa.codegraph.components.server.ComponentTestBase;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactServiceTest extends ComponentTestBase {

    @Autowired
    private Session session;

    @Autowired
    private ArtifactService artifactService;

    @Test
    public void shouldReturnTheVersionsOfAnArtifactAndDeclaredDependencies() {
        session.save(new ArtifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.0"));
        session.save(new ArtifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));

        Set<AvailableVersion> versions = artifactService.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is((3)));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.0")),
            new AvailableVersion(new Version("1.1"))
        ));
    }

    @Test
    public void shouldReturnTheArtifactsBelongingToAnOrganization() {
        session.save(new ArtifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.0"));
        session.save(new ArtifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));
        session.save(new ArtifactNode("com.dnfeitosa.codegraph.foo", "codegraph-foo", "1.1"));

        Set<Artifact> artifacts = artifactService.getArtifactsFromOrganization("com.dnfeitosa.codegraph");

        assertThat(artifacts.size(), is(2));
        assertThat(artifacts, hasItems(
            new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.0")),
            new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.1"))
        ));
    }
}
