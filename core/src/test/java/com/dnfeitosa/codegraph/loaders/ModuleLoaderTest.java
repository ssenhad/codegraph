package com.dnfeitosa.codegraph.loaders;

import com.dnfeitosa.codegraph.model.IvyFile;
import com.dnfeitosa.codegraph.model.Module;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModuleLoaderTest {

	private final IvyFile ivyFile = new IvyFile(TestContext.descriptorLocation("ivy.xml"));
	private ModuleLoader moduleLoader;

	@Before
	public void setUp() {
		moduleLoader = new ModuleLoader();
	}

	@Test
	public void shouldCreateAModuleBasedOnItsIvyConfiguration() {
		Module module = moduleLoader.apply(ivyFile);

		assertThat(module.getName(), is(ivyFile.getModuleName()));
		assertThat(module.getDependencies(), is(ivyFile.getDependencies()));
		assertThat(module.getLocation(), is(ivyFile.getLocation()));
		assertThat(module.getExportTypes(), is(ivyFile.getExportTypes()));
	}
}