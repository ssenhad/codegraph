package com.dnfeitosa.codegraph.core.model;

import org.junit.Test;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.codegraph.core.model.ArtifactType.JAR;
import static com.dnfeitosa.codegraph.core.model.ArtifactType.CONFIG;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ApplicationTest {

	private final List<Jar> dependencies = Collections.emptyList();
	private final Set<ArtifactType> exports = EnumSet.of(JAR, CONFIG);

	private final Module module4 = new Module("app4-Module4", "codebase/application4/app4-Module4",
			dependencies, exports);
	private final Module module2 = new Module("app4-Module2", "codebase/application4/app4-Module2", dependencies, exports);

	private final Application application = new Application("application4", asList(module4, module2));

	@Test
	public void whenCreatingAModule() {
		shouldAssignTheApplicationIntoTheModules();
	}

	private void shouldAssignTheApplicationIntoTheModules() {
		assertThat(module4.getApplication(), sameInstance(application));
		assertThat(module2.getApplication(), sameInstance(application));
	}

	@Test
	public void shouldGetAModuleByItsName() {
		Module module = application.getModule("app4-Module2");

		assertThat(module, sameInstance(module2));
	}
}
