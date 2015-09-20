package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.Jar;
import com.dnfeitosa.codegraph.db.graph.repositories.GraphJarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.dnfeitosa.coollections.Coollections.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-db.xml", "classpath:/codegraph-db-test.xml" })
@ActiveProfiles("test")
@Transactional
public class JarRepositoryTest {

    @Autowired
    private GraphJarRepository repository;

    @Test
    public void shouldSaveASingleJarInstanceBasedOnTheCombinationOfFields() {
        repository.save(commonsLogging());
        repository.save(commonsLogging());

        Iterable<Jar> all = repository.findAll();

        assertThat($(all).toList().size(), is(1));
    }

    private Jar commonsLogging() {
        Jar commonsLogging = new Jar();
        commonsLogging.setOrganization("apache");
        commonsLogging.setName("commons-logging");
        commonsLogging.setVersion("1.0");
        commonsLogging.prepare();
        return commonsLogging;
    }
}
