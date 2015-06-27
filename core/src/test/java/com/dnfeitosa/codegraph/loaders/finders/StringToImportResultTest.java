package com.dnfeitosa.codegraph.loaders.finders;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.dnfeitosa.codegraph.loaders.finders.code.Import;
import com.dnfeitosa.codegraph.loaders.finders.code.ImportResult;

public class StringToImportResultTest {

	private StringToImportResult function;

	@Before
	public void setUp() {
		function = new StringToImportResult();
	}

	@Test
	public void shouldConvertAClassImport() {
		ImportResult result = function
				.apply("./src/com/company/ClassFile.java:import com.company.AnotherClass");

		assertThat(result.getFileName(), is("src/com/company/ClassFile.java"));
		assertEquals(new Import("com.company.AnotherClass"), result.getImported());
	}

	@Test
	public void shouldConvertAStaticImport() {
		ImportResult result = function
				.apply("./src/com/company/ClassFile.java:import static com.company.AnotherClass.method;");

		assertThat(result.getFileName(), is("src/com/company/ClassFile.java"));
		assertEquals(new Import("com.company.AnotherClass"), result.getImported());
	}

	@Test
	public void shouldConvertAnInnerClassImportToClassImport() {
		ImportResult result = function
				.apply("./src/com/company/ClassFile.java:import com.company.AnotherClass.*;");

		assertThat(result.getFileName(), is("src/com/company/ClassFile.java"));
		assertEquals(new Import("com.company.AnotherClass"), result.getImported());
	}
}