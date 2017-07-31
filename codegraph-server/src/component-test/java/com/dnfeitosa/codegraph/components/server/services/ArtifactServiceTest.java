package com.dnfeitosa.codegraph.components.server.services;

import com.dnfeitosa.codegraph.components.server.ComponentTestBase;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.DependencyNode;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static com.dnfeitosa.codegraph.core.models.AvailableVersion.Availability.ARTIFACT;
import static com.dnfeitosa.codegraph.core.models.AvailableVersion.Availability.DEPENDENCY;
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
        session.save(new DependencyNode("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));
        session.save(new DependencyNode("com.dnfeitosa.codegraph", "codegraph-core", "1.2"));

        Set<AvailableVersion> versions = artifactService.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is((3)));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.0"), ARTIFACT),
            new AvailableVersion(new Version("1.1"), ARTIFACT, DEPENDENCY),
            new AvailableVersion(new Version("1.2"), DEPENDENCY)
        ));
    }
}
