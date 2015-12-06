package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("serial")
public class ApplicationNodeConverterTest {

	private final String applicationName = "name";
	private final List<Module> modules = asList(
        new Module(null, null, null, null, null),
        new Module(null, null, null, null, null)
    );

	private final Application application = new Application(applicationName, modules);

	private final ApplicationNode node = new ApplicationNode() {
		{
			setId(1L);
			setName(applicationName);
			setModules(new HashSet<ModuleNode>() {
				{
					add(new ModuleNode());
					add(new ModuleNode());
				}
			});
		}
	};

	private ApplicationConverter converter;

	@Before
	public void setUp() {
		converter = new ApplicationConverter(new ModuleConverter(new JarConverter(), new ArtifactConverter()));
	}

	@Test
	public void shouldConvertAnApplicationToNode() {
		ApplicationNode node = converter.toNode(application);

		assertThat(node.getName(), is(applicationName));
		assertThat(node.getModules().size(), is(2));
	}

	@Test
	public void shouldConvertANodeToApplication() {
		Application application = converter.fromNode(node);

		assertThat(application.getName(), is(applicationName));
		assertThat(application.getModules().size(), is(2));
	}

}