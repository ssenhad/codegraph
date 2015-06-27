package com.dnfeitosa.codegraph.filesystem;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PathTest {

	@Test
	public void shouldJoinThePathPartsWithADirectorySeparator() {
		assertThat(Path.join("a", "b", "c"), is("a/b/c"));
	}
}