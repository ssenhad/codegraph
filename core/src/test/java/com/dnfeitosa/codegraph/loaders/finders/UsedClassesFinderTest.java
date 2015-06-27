package com.dnfeitosa.codegraph.loaders.finders;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.loaders.finders.code.UsageResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.dnfeitosa.codegraph.testing.TestContext.vraptor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
public class UsedClassesFinderTest {

	private UsedClassesFinder usedClassesFinder;
	Terminal terminal = new Terminal();

	@Before
	public void setUp() {
		usedClassesFinder = new UsedClassesFinder(terminal);
	}

	@Test
	public void shouldReturnTheClassesUsedByTheClassesOfAModule() {
		String vRaptorCore = vraptor().locationOf("vraptor-core");

		Set<UsageResult> usageResults = usedClassesFinder.findUsedClassIn(vRaptorCore);

		assertThat(usageResults.size(), is(13099));
	}
}
