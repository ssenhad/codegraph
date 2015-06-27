package com.dnfeitosa.codegraph.loaders.finders;

import com.dnfeitosa.codegraph.commandline.Terminal;
import org.junit.Test;

import java.util.List;

import static com.dnfeitosa.codegraph.testing.TestContext.FAKE_CODEBASE;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class ApplicationsFinderTest {

	private final ApplicationsFinder finder = new ApplicationsFinder(new Terminal());

	@Test
	public void shouldFindTheApplicationsInALocation() {
		List<String> applications = finder.findApplicationsIn(FAKE_CODEBASE);

		assertThat(applications.size(), is(3));
		assertThat(applications, not(hasItem("build")));
		assertThat(applications, hasItems("mirror", "vraptor4", "ivy-based-application"));
	}
}