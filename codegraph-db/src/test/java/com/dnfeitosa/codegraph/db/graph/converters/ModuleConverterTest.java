package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ModuleConverterTest {

	private final String moduleName = "moduleName";
	private final String organization = "organization";
	private final String version = "version";
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

	private final Module module = new Module(moduleName, organization, version, dependencies, exportTypes);

	private final ModuleNode moduleNode = new ModuleNode() {
		{
			setId(1L);
			setName(moduleName);
            setOrganization(organization);
            setVersion(version);
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
                    add(new ModuleNode() {{ setName("module-1"); }});
                    add(new ModuleNode() {{ setName("module-2"); }});
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
        assertThat(node.getVersion(), is(version));
        assertThat(node.getArtifacts().size(), is(2));
		assertThat(node.getDependencies().size(), is(4));
	}

	@Test
	public void shouldConvertANodeToModule() {
		Module module = converter.fromNode(moduleNode);

		assertThat(module.getName(), is(moduleName));
		assertThat(module.getOrganization(), is(organization));
		assertThat(module.getVersion(), is(version));
		assertThat(module.getDependencies().size(), is(2));
		assertThat(module.getExportTypes().size(), is(2));
    }

    @Test
    public void shouldReturnNullWhenModuleNodeIsNull() {
        assertNull(converter.fromNode(null));
    }

    @Test
    public void doesNotFailWhenApplicationInfoIsNotPresent() {
        moduleNode.setApplication(null);

        Module module = converter.fromNode(moduleNode);

        assertThat(module.getName(), Matchers.is(moduleName));
        assertNull(module.getApplication());
    }
}
