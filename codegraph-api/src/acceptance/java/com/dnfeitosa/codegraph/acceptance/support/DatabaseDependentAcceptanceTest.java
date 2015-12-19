package com.dnfeitosa.codegraph.acceptance.support;

import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import com.dnfeitosa.codegraph.db.graph.repositories.ApplicationRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.dnfeitosa.coollections.Coollections.asSet;
import static java.util.Arrays.asList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-api.xml", "classpath:/codegraph-db.xml" , "classpath:/codegraph-db-acceptance.xml" })
@ActiveProfiles("acceptance")
@Transactional
@Ignore
public class DatabaseDependentAcceptanceTest {

    @Autowired
    protected ApplicationRepository applicationRepository;

    @Autowired
    protected Neo4jTemplate neo4jTemplate;

    @Before
    public void setUp() {
        neo4jTemplate.query(" MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r", new HashMap<>());
        ApplicationNode vraptor = createVraptor();
        ApplicationNode mirror = createMirror();

        applicationRepository.save(asList(vraptor, mirror));
    }

    private ApplicationNode createMirror() {
        ModuleNode module = new ModuleNode();
        module.setName("mirror");
        module.setDependencies(asSet());

        ModuleNode objenesis = new ModuleNode();
        objenesis.setName("objenesis");
        objenesis.setOrganization("org.objenesis");
        objenesis.setVersion("1.2");

        ModuleNode cglib = new ModuleNode();
        cglib.setName("cglib-nodep");
        cglib.setOrganization("cglib");
        cglib.setVersion("2.1_3");

        ModuleNode junit = new ModuleNode();
        junit.setName("junit");
        junit.setOrganization("junit");
        junit.setVersion("4.7");
        module.setDependencies(asSet(objenesis, cglib, junit));

        ApplicationNode application = new ApplicationNode();
        application.setName("mirror");
        application.setModules(asSet(module));

        return application;
    }

    private ApplicationNode createVraptor() {
        ModuleNode core = new ModuleNode();
        core.setName("vraptor-core");

        ModuleNode site = new ModuleNode();
        site.setName("vraptor-site");

        ApplicationNode vraptor = new ApplicationNode();
        vraptor.setName("vraptor4");
        vraptor.setModules(asSet(core, site));

        return vraptor;
    }
}
