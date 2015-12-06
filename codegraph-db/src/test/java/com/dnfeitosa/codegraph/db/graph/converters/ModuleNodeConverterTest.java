package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModuleNodeConverterTest {

	private final String moduleName = "moduleName";
	private final String organization = "organization";
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

	private final Module module = new Module(moduleName, organization, dependencies, exportTypes);

	private final ModuleNode moduleNodeNode = new ModuleNode() {
		{
			setId(1L);
			setName(moduleName);
            setApplication(new ApplicationNode() {{
                setName("applicationName");
            }});
			setArtifacts(new HashSet<ArtifactNode>() {
                {
                    add(new ArtifactNode() {{
                        setName("JAR");
                    }});
                    add(new ArtifactNode() {{
                        setName("CONFIG");
                    }});
                }
            });
            setDependencies(new HashSet<ModuleNode>() {
                {
                    add(new ModuleNode());
                    add(new ModuleNode());
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
		ModuleNode node = converter.toNode(module);

		assertThat(node.getName(), is(moduleName));
		assertThat(node.getOrganization(), is(organization));
		assertThat(node.getArtifacts().size(), is(2));
		assertThat(node.getDependencies().size(), is(4));
	}

	@Test
	public void shouldConvertANodeToModule() {
		com.dnfeitosa.codegraph.core.model.Module module = converter.fromNode(moduleNodeNode);

		assertThat(module.getName(), is(moduleName));
		assertThat(module.getDependencies().size(), is(2));
		assertThat(module.getExportTypes().size(), is(2));
    }
}
