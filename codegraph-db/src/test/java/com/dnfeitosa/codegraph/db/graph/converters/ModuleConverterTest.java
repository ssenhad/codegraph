package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.db.graph.nodes.Artifact;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModuleConverterTest {

	private final String moduleName = "moduleName";
	private final List<Jar> dependencies = asList(
        new Jar("apache", "commons-lang", null),
        new Jar("apache", "commons-io", null),
        new Jar("org", "hamcrest", null),
        new Jar("org", "junit", null)
    );
	private final Set<ArtifactType> exportTypes = new HashSet<ArtifactType>() {
		{
			add(new ArtifactType("jar"));
			add(new ArtifactType("config"));
		}
	};

	private final com.dnfeitosa.codegraph.core.model.Module module = new com.dnfeitosa.codegraph.core.model.Module(moduleName, null, dependencies, exportTypes);

	private final Module moduleNode = new Module() {
		{
			setId(1L);
			setName(moduleName);
			setArtifacts(new HashSet<Artifact>() {
				{
					add(new Artifact() {{
                        setName("JAR");
                    }});
                    add(new Artifact() {{
                        setName("CONFIG");
                    }});
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
		assertThat(node.getDependencies().size(), is(4));
	}

	@Test
	public void shouldConvertANodeToModule() {
		com.dnfeitosa.codegraph.core.model.Module module = converter.fromNode(moduleNode);

		assertThat(module.getName(), is(moduleName));
		assertThat(module.getDependencies().size(), is(2));
		assertThat(module.getExportTypes().size(), is(2));
	}
}
