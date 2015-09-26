package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.services.ApplicationService;
import com.dnfeitosa.coollections.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;
import static com.dnfeitosa.coollections.Coollections.asSet;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-db.xml", "classpath:/codegraph-db-test.xml" })
@ActiveProfiles("test")
@Transactional
public class ApplicationNodeServiceTest {

	private final List<Module> modules = asList(
			module("app4-Module4",
                asList(
                    jar("org", "app2-Module2", "1.0"),
                    jar("org", "module", "1.0")),
                asSet(
                    new ArtifactType("config"),
                    new ArtifactType("jar"))
            ),
			module("app4-Module2",
                asList(
                    jar("org", "app2-Module2", "1.0"),
                    jar("org", "module", "1.0"),
                    jar("apache", "commons-lang", "1.0")),
                asSet(
                    new ArtifactType("config"),
                    new ArtifactType("jar"))
            )
    );

	private final String app4 = "application4";
	private final Application toSave = new Application(app4, modules);

	@Autowired
	private ApplicationService service;

	@Test
	public void shouldSaveAnApplication() {
		service.save(toSave);

		Application loaded = service.find(app4);
		assertThat(loaded.getName(), is(app4));
		assertThat(loaded.getModules().size(), is(2));

        List<Module> modules = loaded.getModules();
        Module mod4 = findModule(modules, "app4-Module4");
		assertThat(mod4.getName(), is("app4-Module4"));

		Module mod2 = findModule(modules, "app4-Module2");
		assertThat(mod2.getName(), is("app4-Module2"));
	}

	@Test
	public void shouldGetAllApplications() {
		service.save(new Application("application2"));
		service.save(new Application("application3"));
		service.save(new Application("application1"));

		List<Application> all = service.getAll();
		assertThat(all.size(), is(3));

		assertThat($(all).collect(names()), hasItems("application2", "application3", "application1"));
	}

	private Function<Application, String> names() {
		return input -> input.getName();
	}

	@Test
	public void shouldGetAllApplicationNames() {
		service.save(new Application("application2"));
		service.save(new Application("application3"));
		service.save(new Application("application1"));

		List<String> names = service.getApplicationNames();
		assertThat(names.size(), is(3));
		assertThat(names, hasItems("application2", "application3", "application1"));
	}

	private Module findModule(List<Module> modules, String name) {
		for (Module module : modules) {
			if (name.equals(module.getName())) {
				return module;
			}
		}

		fail(format("Module %s was not loaded.", name));
		return null;
	}

	private Module module(String name, List<Jar> dependencies, Set<ArtifactType> artifactTypes) {
		return new Module(name, null, dependencies, artifactTypes);
	}

	private Jar jar(String organization, String name, String version) {
		return new Jar(organization, name, version);
	}
}
