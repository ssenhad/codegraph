package com.dnfeitosa.codegraph.core.loaders.classes;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.loaders.ClassFileLoader;
import com.dnfeitosa.codegraph.core.loaders.finders.ImportsFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.IvyFileFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.JavaFileFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.UsedClassesFinder;
import com.dnfeitosa.codegraph.core.loaders.finders.code.ClassFile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.dnfeitosa.codegraph.testing.TestContext.FAKE_CODEBASE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Ignore
public class ApplicationClassLoaderTest {

	private ApplicationClassLoader classLoader;

	@Before
	public void setUp() {
		Terminal terminal = new Terminal();
		IvyFileFinder ivyFileFinder = new IvyFileFinder(terminal);
		JavaFileFinder fileFinder = new JavaFileFinder(terminal);
		ImportsFinder importsFinder = new ImportsFinder(terminal);
		UsedClassesFinder usedClassesFinder = new UsedClassesFinder(terminal);
		ClassFileLoader classFileLoader = new ClassFileLoader(fileFinder, importsFinder, usedClassesFinder);
		classLoader = new ApplicationClassLoader(ivyFileFinder, classFileLoader);
	}

	@Test
	public void shouldLoadTheClassFilesOfAnApplication() {
		List<ClassFile> classFiles = classLoader.loadFor(FAKE_CODEBASE, "mirror");

		assertThat(classFiles.size(), is(5));
	}
}
