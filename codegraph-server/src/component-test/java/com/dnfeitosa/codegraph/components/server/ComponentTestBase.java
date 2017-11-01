package com.dnfeitosa.codegraph.components.server;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Artifacts;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.experimental.tests.checks.StorageChecks;
import com.dnfeitosa.codegraph.experimental.tests.finders.ArtifactFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ComponentTestConfiguration.class)
@TestPropertySource({ "classpath:/component-test.properties" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Ignore
public class ComponentTestBase {

    @Autowired
    private Session session;

    @Autowired
    protected StorageChecks db;

    @After
    public void tearDown() {
        /**
         * deletes all data after running the test
         */
        session.purgeDatabase();
    }

    protected Artifacts artifacts;

    @Before
    public void setUp() {
        artifacts = new Artifacts();
    }

    protected ArtifactFinder find(String id) {
        return new ArtifactFinder(id, Artifact::getId);
    }

    protected ArtifactNode artifactNode(String organization, String name, String version) {
        return new ArtifactNode(organization, name, version);
    }

    public Artifact artifact(String organization, String name, String version) {
        return artifacts.artifact(organization, name, new Version(version));
    }

    public Dependency dependency(String organization, String name, String version, Set<String> configurations) {
        return new Dependency(artifact(organization, name, version), configurations);
    }

    public Session getSession() {
        return session;
    }
}
