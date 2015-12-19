package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JarConverterTest {

	private final String organization = "organization";
	private final String name = "name";
	private final String version = "version";

	private final Jar jar = new Jar(organization, name, version);
	private final ModuleNode node = new ModuleNode() {
		{
			setOrganization(organization);
			setName(name);
			setVersion(version);
		}
	};

	private JarConverter converter;

	@Before
	public void setUp() {
		converter = new JarConverter();
	}

	@Test
	public void shouldConvertAJarToNode() {
		ModuleNode node = converter.toNode(jar);

		assertThat(node.getOrganization(), is(organization));
		assertThat(node.getName(), is(name));
		assertThat(node.getVersion(), is(version));
	}

	@Test
	public void shouldConvertANodeToJar() {
		Jar jar = converter.fromNode(node);

		assertThat(jar.getOrganization(), is(organization));
		assertThat(jar.getName(), is(name));
		assertThat(jar.getVersion(), is(version));
	}

}