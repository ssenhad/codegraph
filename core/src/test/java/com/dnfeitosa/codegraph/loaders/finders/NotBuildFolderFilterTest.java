package com.dnfeitosa.codegraph.loaders.finders;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotBuildFolderFilterTest {

	@Test
	public void shouldReportWhenItIsTheBuildFolder() {
		NotBuildFolderFilter filter = new NotBuildFolderFilter();

		assertFalse(filter.matches("build"));
		assertFalse(filter.matches("BUILD"));
		assertFalse(filter.matches("Build"));
		assertTrue(filter.matches("something"));
		assertTrue(filter.matches(""));
	}
}
