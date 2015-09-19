package com.dnfeitosa.codegraph.core.commandline;

import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class TerminalTest {

	private Terminal terminal;

	@Before
	public void setUp() {
		terminal = new Terminal();
	}

	@Test
	public void shouldExecuteACommandAndReturnTheOutputAsAString() {
		List<String> output = terminal.execute("ls", TestContext.FAKE_CODEBASE);

		assertThat(output, hasItems("mirror", "vraptor4"));
	}

	@Test(expected = TerminalException.class)
	public void shouldThrowExceptionWhenCommandIsInvalid() {
		terminal.execute("meh");
	}

	@Test(expected = TerminalException.class)
	public void shouldThrowExceptionWhenCommandDoesNotExecuteProperly() {
		terminal.execute("ls ~/dont-exist");
	}
}
