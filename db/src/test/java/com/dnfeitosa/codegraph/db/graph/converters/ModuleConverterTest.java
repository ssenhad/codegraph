package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.db.graph.nodes.Artifact;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import com.dnfeitosa.codegraph.model.ArtifactType;
import com.dnfeitosa.codegraph.model.Jar;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.codegraph.model.ArtifactType.CONFIG;
import static com.dnfeitosa.codegraph.model.ArtifactType.JAR;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Ignore
public class ModuleConverterTest {

	private final String moduleName = "moduleName";
	private final List<Jar> dependencies = asList(new Jar("apache", null, null), new Jar("apache", null, null),
			new Jar("org", null, null), new Jar("org", null, null));
	private final Set<ArtifactType> exportTypes = new HashSet<ArtifactType>() {
		{
			add(JAR);
			add(CONFIG);
		}
	};

	private final com.dnfeitosa.codegraph.model.Module module = new com.dnfeitosa.codegraph.model.Module(moduleName, null, dependencies, exportTypes);

	private final Module node = new Module() {
		{
			setId(1L);
			setName(moduleName);
			setArtifacts(new HashSet<Artifact>() {
				{
					add(new Artifact() {
						{
							setName("JAR");
						}
					});
					add(new Artifact() {
						{
							setName("CONFIG");
						}
					});
				}
			});
			setDependencies(new HashSet<com.dnfeitosa.codegraph.db.graph.nodes.Jar>() {
                {
                    add(new com.dnfeitosa.codegraph.db.graph.nodes.Jar());
                    add(new com.dnfeitosa.codegraph.db.graph.nodes.Jar());
                }
            });
		}
	};

	private ModuleConverter converter;

	@Before
	public void setUp() {
		converter = new ModuleConverter(new JarConverter(), new ArtifactConverter());
	}

	@Test
	public void shouldConvertAModuleToNode() {
		Module node = converter.toNode(module);

		assertThat(node.getName(), is(moduleName));
		assertThat(node.getArtifacts().size(), is(2));
	}

	@Test
	public void shouldConvertANodeToModule() {
		com.dnfeitosa.codegraph.model.Module module = converter.fromNode(node);

		assertThat(module.getName(), is(moduleName));
		assertThat(module.getDependencies().size(), is(4));
		assertThat(module.getExportTypes().size(), is(2));
	}
}
