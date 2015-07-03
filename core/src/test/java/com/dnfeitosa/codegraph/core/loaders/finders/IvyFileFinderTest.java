package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.dnfeitosa.codegraph.testing.TestContext.ivyBased;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IvyFileFinderTest {

	@Test
    @Ignore
	public void shouldFindTheIvyFilesFromASpecificLocation() {
		IvyFileFinder finder = new IvyFileFinder(new Terminal());

		List<ModuleDescriptor> files = finder.findFilesIn(ivyBased().location());

		assertThat(files.size(), is(2));
	}
}