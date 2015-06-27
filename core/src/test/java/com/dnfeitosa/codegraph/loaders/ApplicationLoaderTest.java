package com.dnfeitosa.codegraph.loaders;

import static org.apache.commons.lang.StringUtils.join;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.dnfeitosa.codegraph.loaders.finders.IvyFileFinder;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.model.Application;

import java.util.Arrays;

public class ApplicationLoaderTest {

	private ApplicationLoader loader;

	@Before
	public void setUp() {
		Terminal terminal = new Terminal();
		loader = new ApplicationLoader(new ModulesLoader(new IvyFileFinder(terminal), new ModuleLoader()));
	}

	@Test
	public void shouldLoadAnApplication() {
		Application application = loader.load(TestContext.FAKE_CODEBASE, "application4");

		assertThat(application.getName(), is("application4"));
		assertThat(application.getLocation(), is(StringUtils.join(Arrays.asList(TestContext.FAKE_CODEBASE, "application4"), "/")));
	}
}