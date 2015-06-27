package com.dnfeitosa.codegraph.db.graph.repositories;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import com.dnfeitosa.codegraph.db.graph.nodes.Application;
import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import com.dnfeitosa.coollections.Coollections;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.coollections.decorators.CoolList;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-db.xml", "classpath:/codegraph-db-test.xml" })
@ActiveProfiles("test")
@Transactional
public class GraphModuleRepositoryTest {

	@Autowired
	private GraphApplicationRepository applicationRepository;

	@Autowired
	private GraphModuleRepository moduleRepository;

	@Before
	public void setUp() {
		Application app1 = new Application();
		app1.setName("application4");
		app1.setModules(Coollections.asSet(module("app4-Module4", "app2-Module2", "module")));

		Application app2 = new Application();
		app2.setName("application2");
		app2.setModules(Coollections.asSet(module("app2-Module2", "module")));

		Application app3 = new Application();
		app3.setName("application");
		app3.setModules(Coollections.asSet(module("module")));

		applicationRepository.save(asList(app1, app2, app3));
	}

	@Test
	public void shouldReturnTheFullImpactZoneOfAModule() {
		List<ImpactResult> list = moduleRepository.fullImpactOf("module");

		assertThat(list.size(), is(3));
		assertIsImpactResult(list.get(0), "module", "app4-Module4");
		assertIsImpactResult(list.get(1), "module", "app2-Module2");
		assertIsImpactResult(list.get(2), "app2-Module2", "app4-Module4");
	}

	@Test
	public void shouldReturnTheImmediateImpactZoneOfAModule() {
		List<ImpactResult> list = moduleRepository.immediateImpactOf("module");

		assertThat(list.size(), is(2));
		assertIsImpactResult(list.get(0), "module", "app4-Module4");
		assertIsImpactResult(list.get(1), "module", "app2-Module2");
	}

	private void assertIsImpactResult(ImpactResult result, String impactor, String impacted) {
		assertThat(result.getImpactor().getName(), is(impactor));
		assertThat(result.getImpacted().getName(), is(impacted));
	}

	private Module module(String name, String... dependencies) {
		Module module = new Module();
		module.setName(name);
//		module.setDependencies(toModules(dependencies).toSet());
		return module;
	}

	private CoolList<Module> toModules(String... dependencies) {
		Function<String, Module> toModule = new Function<String, Module>() {
			@Override
			public Module apply(String name) {
				Module module = new Module();
				module.setName(name);
				return module;
			}
		};
		return $(asList(dependencies)).collect(toModule);
	}

}
