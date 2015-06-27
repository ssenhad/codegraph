package com.dnfeitosa.codegraph.loaders.finders;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.model.IvyFile;
import org.junit.Test;

import java.util.List;

import static com.dnfeitosa.codegraph.testing.TestContext.ivyBased;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IvyFileFinderTest {

	@Test
	public void shouldFindTheIvyFilesFromASpecificLocation() {
		IvyFileFinder finder = new IvyFileFinder(new Terminal());

		List<IvyFile> files = finder.findFilesIn(ivyBased().location());

		assertThat(files.size(), is(2));
	}
}