package com.dnfeitosa.codegraph.core.loaders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.loaders.finders.IvyFileFinder;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModulesLoaderTest {

	private final Terminal terminal = new Terminal();
	private final IvyFileFinder ivyFileFinder = new IvyFileFinder(terminal);

	private final ModuleLoader moduleLoader = new ModuleLoader();

	private ModulesLoader loader;

    @Before
	public void setUp() {
		loader = new ModulesLoader(ivyFileFinder, moduleLoader);
    }

	@Test
    @Ignore
	public void shouldLoadTheModulesBasedOnTheirIvyFiles() {
		List<Module> modules = loader.loadAllFrom(TestContext.ivyBased().location());

		assertThat(modules.size(), is(2));
		assertThat(modules.get(0).getName(), is("module1"));
		assertThat(modules.get(1).getName(), is("module2"));
	}
}
