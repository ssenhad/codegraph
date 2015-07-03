package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JavaFileFinderTest {

	private JavaFileFinder finder;

	@Before
	public void setUp() {
		finder = new JavaFileFinder(new Terminal());
	}

	@Test
	public void shouldFindTheFilesInADirectory() {
		List<String> files = finder.findFilesIn(TestContext.mirror().location());

		assertThat(files.size(), is(178));
	}
}
