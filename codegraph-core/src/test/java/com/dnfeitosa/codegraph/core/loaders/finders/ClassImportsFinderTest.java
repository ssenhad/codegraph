package com.dnfeitosa.codegraph.core.loaders.finders;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.loaders.finders.code.Import;
import com.dnfeitosa.codegraph.core.loaders.finders.code.ImportResult;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ClassImportsFinderTest {

	private final Terminal terminal = new Terminal();

	private ImportsFinder finderClass;

	@Before
	public void setUp() {
		finderClass = new ImportsFinder(terminal);
	}

	@Test
	public void shouldFindTheImportStatements() {
		List<ImportResult> results = finderClass.findImportsIn(TestContext.vraptor().locationOf("vraptor-core"));

		assertThat(results.size(), is(4500));
        System.out.println(results.get(0));
        assertIsResult(results.get(0), "src/main/java/br/com/caelum/vraptor/Accepts.java",
				"java.lang.annotation.Documented");
		assertIsResult(results.get(1), "src/main/java/br/com/caelum/vraptor/Accepts.java",
				"java.lang.annotation.ElementType");
	}

	private void assertIsResult(ImportResult result, String fileName, String imported) {
		assertThat(result.getFileName(), is(fileName));
		assertEquals(new Import(imported), result.getImported());
	}
}
