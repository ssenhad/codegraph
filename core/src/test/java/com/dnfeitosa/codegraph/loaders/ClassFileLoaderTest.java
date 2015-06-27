package com.dnfeitosa.codegraph.loaders;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.loaders.finders.ImportsFinder;
import com.dnfeitosa.codegraph.loaders.finders.JavaFileFinder;
import com.dnfeitosa.codegraph.loaders.finders.UsedClassesFinder;
import com.dnfeitosa.codegraph.loaders.finders.code.ClassFile;
import com.dnfeitosa.codegraph.loaders.finders.code.Import;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.dnfeitosa.codegraph.testing.TestContext.vraptor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class ClassFileLoaderTest {

	private final Terminal terminal = new Terminal();

	private ClassFileLoader fileLoader;

	@Before
	public void setUp() {
		JavaFileFinder fileFinder = new JavaFileFinder(terminal);
		ImportsFinder importsFinder = new ImportsFinder(terminal);
		UsedClassesFinder usedClassesFinder = new UsedClassesFinder(terminal);
		fileLoader = new ClassFileLoader(fileFinder, importsFinder, usedClassesFinder);
	}

	@Test
	public void shouldLoadTheClassesFromAModule() {
		List<ClassFile> classFiles = fileLoader.loadFor("vraptor-core", vraptor().locationOf("vraptor-core"));

		assertThat(classFiles.size(), is(511));

		ClassFile clazz = classFiles.get(0);
		assertThat(clazz.getName(), is("Accepts"));
		assertThat(clazz.getImports().size(), is(5));

		assertThat(clazz.getImports(), hasItems(
            _import("java.lang.annotation.RetentionPolicy"),
            _import("java.lang.annotation.Retention"),
            _import("java.lang.annotation.Target"),
            _import("java.lang.annotation.ElementType"),
            _import("java.lang.annotation.Documented"))
		);
	}

	private Import _import(String qualifiedName) {
		return new Import(qualifiedName);
	}
}
