package com.dnfeitosa.codegraph.components.server;

import org.junit.After;
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
public class ComponentTestBase {

    @Autowired
    private Session session;

    @After
    public void tearDown() {
        /**
         * deletes all data after running the test
         */
        session.purgeDatabase();
    }
}
