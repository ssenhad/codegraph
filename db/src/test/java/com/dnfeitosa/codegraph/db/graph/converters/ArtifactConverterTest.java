package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.db.graph.nodes.Artifact;
import com.dnfeitosa.codegraph.model.ArtifactType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactConverterTest {

	private ArtifactConverter converter;

	@Before
	public void setUp() {
		converter = new ArtifactConverter();
	}

	@Test
	public void shouldConvertAnArtifactTypeToNode() {
		Artifact artifact = converter.toNode(ArtifactType.COOKBOOK);

		assertThat(artifact.getName(), is("COOKBOOK"));
	}

	@Test
	public void shouldConvertANodeToArtifactType() {
		ArtifactType cookbook = converter.fromNode(new Artifact() {
			{
				setName("COOKBOOK");
			}
		});

		assertThat(cookbook, is(ArtifactType.COOKBOOK));
	}

}