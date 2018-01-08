package co.degraph.server;

import co.degraph.server.component.services.GraphChecks;
import co.degraph.core.models.Artifact;
import co.degraph.core.models.Artifacts;
import co.degraph.core.models.Graph;
import co.degraph.experimental.tests.checks.StorageChecks;
import co.degraph.server.services.DependencyEdge;
import co.degraph.server.test.helpers.ModelBuilders;
import co.degraph.server.test.helpers.NodeBuilders;
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
@ContextConfiguration(classes = ComponentTestConfiguration.class)
@TestPropertySource({ "classpath:/component-test.properties" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Ignore
public class ComponentTestBase implements NodeBuilders, ModelBuilders {

    @Autowired
    protected Session session;

    @Autowired
    protected StorageChecks db;

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

    protected GraphChecks check(Graph<Artifact, DependencyEdge> graph) {
        return new GraphChecks(graph);
    }
}
