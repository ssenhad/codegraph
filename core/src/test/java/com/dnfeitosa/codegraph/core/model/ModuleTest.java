package com.dnfeitosa.codegraph.core.model;

import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.List;

import static com.dnfeitosa.codegraph.core.model.ArtifactType.JAR;
import static com.dnfeitosa.codegraph.core.model.ArtifactType.CONFIG;
import static com.dnfeitosa.codegraph.core.model.ArtifactType.WEBAPP;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ModuleTest {

	private final Jar commonsLang = new Jar("apache", "commons-lang", "1.0");
	private final Jar springCore = new Jar("springframework", "spring-core", "1.0");

	private Module module;

	@Before
	public void setUp() {
		module = new Module("aModule", "location", asList(commonsLang, springCore), EnumSet.of(JAR, CONFIG));
	}

	@Test
	public void shouldReturnTheModuleDependencies() {
		List<Jar> dependencies = module.getDependencies();

		assertThat(dependencies.size(), is(2));
		assertThat(dependencies.get(0), sameInstance(commonsLang));
		assertThat(dependencies.get(1), sameInstance(springCore));
	}

	@Test
	public void shouldTellWhetherAModuleDependsOnSomething() {
		assertTrue(module.dependsOn("commons-lang"));
		assertFalse(module.dependsOn("commons-collections"));
	}

    @Test
    public void shouldReturnTheModuleDependenciesByFilter() {
        List<Jar> dependencies = module.getDependencies(jar -> "apache".equals(jar.getOrganization()));


        assertThat(dependencies.size(), is(1));
        assertThat(dependencies.get(0), sameInstance(commonsLang));
    }

	@Test
	public void shouldTellWhetherAModuleExportsAnArtifactType() {
		assertTrue(module.exports(JAR));
		assertTrue(module.exports(CONFIG));
		assertFalse(module.exports(WEBAPP));
	}
}
