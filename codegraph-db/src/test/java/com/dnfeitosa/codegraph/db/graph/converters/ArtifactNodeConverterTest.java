package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.db.graph.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.core.model.ArtifactType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactNodeConverterTest {

	private ArtifactConverter converter;

	@Before
	public void setUp() {
		converter = new ArtifactConverter();
	}

	@Test
	public void shouldConvertAnArtifactTypeToNode() {
		ArtifactNode artifactNode = converter.toNode(new ArtifactType("cookbook"));

		assertThat(artifactNode.getName(), is("cookbook"));
	}

	@Test
	public void shouldConvertANodeToArtifactType() {
		ArtifactType cookbook = converter.fromNode(new ArtifactNode() {
			{
				setName("COOKBOOK");
			}
		});

		assertThat(cookbook, is(new ArtifactType("cookbook")));
	}
}
