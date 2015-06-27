package com.dnfeitosa.codegraph.loaders;

import static com.dnfeitosa.codegraph.loaders.finders.code.ClassFile.FileType.SRC;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.dnfeitosa.codegraph.loaders.finders.code.ClassFile;
import com.dnfeitosa.codegraph.loaders.finders.code.ClassFile.FileType;

public class PathToClassFileFunctionTest {

	private final String moduleName = "moduleName";

	private PathToClassFileFunction fileFunction;

	@Before
	public void setUp() {
		fileFunction = new PathToClassFileFunction(moduleName);
	}

	@Test
	public void shouldBuildAClassObjectFromFileName() {
		ClassFile clazz = fileFunction.apply("src/com/company/model/ClassName.java");

		assertThat(clazz.getName(), is("ClassName"));
		assertThat(clazz.getPackageName(), is("com.company.model"));
		assertThat(clazz.getQualifiedName(), is("com.company.model.ClassName"));
		assertThat(clazz.getModuleName(), is(moduleName));
		assertThat(clazz.getFileType(), is(SRC));

		clazz = fileFunction.apply("test/com/company/model/ClassNameTest.java");
		assertThat(clazz.getName(), is("ClassNameTest"));
		assertThat(clazz.getPackageName(), is("com.company.model"));
		assertThat(clazz.getQualifiedName(), is("com.company.model.ClassNameTest"));
		assertThat(clazz.getModuleName(), is(moduleName));
		assertThat(clazz.getFileType(), is(FileType.TEST));
	}
}