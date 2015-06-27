package com.dnfeitosa.codegraph.loaders.finders.code;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ClassImportTest {

	@Test
	public void shouldGetTheClassAndPackageName() {
		Import anImport = new Import("com.company.ClassName");

		assertThat(anImport.getClassName(), is("ClassName"));
		assertThat(anImport.getPackageName(), is("com.company"));
	}

}