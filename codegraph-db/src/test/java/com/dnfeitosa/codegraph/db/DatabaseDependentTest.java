package com.dnfeitosa.codegraph.db;

import com.dnfeitosa.codegraph.db.graph.nodes.builders.ApplicationNodeBuilder;
import com.dnfeitosa.codegraph.db.graph.nodes.builders.ApplicationNodeBuilders;
import com.dnfeitosa.codegraph.db.graph.nodes.builders.Builders;
import com.dnfeitosa.codegraph.db.graph.nodes.builders.ModuleNodeBuilder;
import com.dnfeitosa.codegraph.db.graph.nodes.builders.ModuleNodeBuilders;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/codegraph-db.xml", "classpath:/codegraph-db-test.xml"})
@ActiveProfiles("test")
@Transactional
@Ignore
public class DatabaseDependentTest {

    private Builders builders = new Builders();

    @Autowired
    protected Neo4jTemplate neo4jTemplate;

    @Before
    public void setUp() {
        neo4jTemplate.query(" MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r", new HashMap<>());
    }

    protected ApplicationNodeBuilder application(String name) {
        return builders.application(name);
    }

    protected ModuleNodeBuilder module(String name) {
        return builders.module(name);
    }

    protected ModuleNodeBuilders modules(String... names) {
        return builders.modules(names);
    }

    protected ApplicationNodeBuilders applications(String... names) {
        return builders.applications(names);
    }
}