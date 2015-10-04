package com.dnfeitosa.codegraph.acceptance.support;

import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import com.dnfeitosa.codegraph.db.graph.repositories.ApplicationRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.dnfeitosa.coollections.Coollections.asSet;
import static java.util.Arrays.asList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-api.xml", "classpath:/codegraph-db.xml" , "classpath:/codegraph-db-acceptance.xml" })
@ActiveProfiles("acceptance")
@Transactional
@Ignore
public class DatabaseDependentTest {

    @Autowired
    protected ApplicationRepository applicationRepository;

    @Before
    public void setUp() {
        ApplicationNode vraptor = createVraptor();
        ApplicationNode mirror = createMirror();

        applicationRepository.save(asList(vraptor, mirror));
    }

    private ApplicationNode createMirror() {
        ModuleNode module = new ModuleNode();
        module.setName("mirror");

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
