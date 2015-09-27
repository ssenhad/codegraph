package com.dnfeitosa.codegraph.db.graph.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import com.dnfeitosa.coollections.Coollections;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.coollections.decorators.CoolList;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-db.xml", "classpath:/codegraph-db-test.xml" })
@ActiveProfiles("test")
@Transactional
public class GraphModuleNodeRepositoryTest {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Before
	public void setUp() {
		ApplicationNode app1 = new ApplicationNode();
		app1.setName("application4");
		app1.setModules(Coollections.asSet(module("app4-Module4", "app2-Module2", "module")));

		ApplicationNode app2 = new ApplicationNode();
		app2.setName("application2");
		app2.setModules(Coollections.asSet(module("app2-Module2", "module")));

		ApplicationNode app3 = new ApplicationNode();
		app3.setName("application");
		app3.setModules(Coollections.asSet(module("module")));

		applicationRepository.save(asList(app1, app2, app3));
	}

	@Test
    @Ignore
	public void shouldReturnTheFullImpactZoneOfAModule() {
		List<ImpactResult> list = moduleRepository.fullImpactOf("module");

		assertThat(list.size(), is(3));
		assertIsImpactResult(list.get(0), "module", "app4-Module4");
		assertIsImpactResult(list.get(1), "module", "app2-Module2");
		assertIsImpactResult(list.get(2), "app2-Module2", "app4-Module4");
	}

	@Test
    @Ignore
	public void shouldReturnTheImmediateImpactZoneOfAModule() {
		List<ImpactResult> list = moduleRepository.immediateImpactOf("module");

		assertThat(list.size(), is(2));
		assertIsImpactResult(list.get(0), "module", "app4-Module4");
		assertIsImpactResult(list.get(1), "module", "app2-Module2");
	}

    @Test
    public void shouldReturnTheModuleByName() {
        ModuleNode moduleNode = moduleRepository.findByName("module");
        assertThat(moduleNode.getName(), is("module"));
    }

	private void assertIsImpactResult(ImpactResult result, String impactor, String impacted) {
		assertThat(result.getImpactor().getName(), is(impactor));
		assertThat(result.getImpacted().getName(), is(impacted));
	}

	private ModuleNode module(String name, String... dependencies) {
		ModuleNode moduleNode = new ModuleNode();
		moduleNode.setName(name);
//		moduleNode.setDependencies(toModules(dependencies).toSet());
		return moduleNode;
	}

	private CoolList<ModuleNode> toModules(String... dependencies) {
		Function<String, ModuleNode> toModule = new Function<String, ModuleNode>() {
			@Override
			public ModuleNode apply(String name) {
				ModuleNode moduleNode = new ModuleNode();
				moduleNode.setName(name);
				return moduleNode;
			}
		};
		return $(asList(dependencies)).collect(toModule);
	}

}
