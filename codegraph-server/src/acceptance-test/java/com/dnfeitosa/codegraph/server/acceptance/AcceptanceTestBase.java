package com.dnfeitosa.codegraph.server.acceptance;

import com.dnfeitosa.codegraph.core.models.Artifacts;
import com.dnfeitosa.codegraph.experimental.tests.checks.StorageChecks;
import com.dnfeitosa.codegraph.server.acceptance.api.controllers.ArtifactResourceChecks;
import com.dnfeitosa.codegraph.server.acceptance.api.controllers.GraphResourceChecks;
import com.dnfeitosa.codegraph.server.api.resources.GraphResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.test.helpers.ModelBuilders;
import com.dnfeitosa.codegraph.server.test.helpers.NodeBuilders;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AcceptanceTestConfiguration.class)
@TestPropertySource({ "classpath:/acceptance-test.properties" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Ignore
public class AcceptanceTestBase implements NodeBuilders, ModelBuilders {

    @Autowired
    protected StorageChecks db;

    @Autowired
    protected Session session;

    protected Artifacts artifacts;

    @Before
    public void setUp() {
        artifacts = new Artifacts();
    }

    @After
    public void tearDown() {
        /**
         * deletes all data after running the test
         */
        session.purgeDatabase();
    }

    @Override
    public Artifacts getArtifacts() {
        return artifacts;
    }

    protected ArtifactResourceChecks check(ArtifactResource resource) {
        return new ArtifactResourceChecks(resource);
    }

    protected GraphResourceChecks check(GraphResource nodeResource) {
        return new GraphResourceChecks(nodeResource);
    }
}
