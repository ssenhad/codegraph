package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.db.graph.nodes.Application;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("serial")
public class ApplicationConverterTest {

	private final String applicationName = "name";
	private final String location = "location";
	private final List<com.dnfeitosa.codegraph.model.Module> modules = asList(new com.dnfeitosa.codegraph.model.Module(null, null, null, null), new com.dnfeitosa.codegraph.model.Module(null, null, null, null));

	private final com.dnfeitosa.codegraph.model.Application application = new com.dnfeitosa.codegraph.model.Application(
			applicationName, location, modules);

	private final Application node = new Application() {
		{
			setId(1L);
			setName(applicationName);
			setModules(new HashSet<Module>() {
				{
					add(new Module());
					add(new Module());
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
		Application node = converter.toNode(application);

		assertThat(node.getName(), is(applicationName));
		assertThat(node.getModules().size(), is(2));
	}

	@Test
	public void shouldConvertANodeToApplication() {
		com.dnfeitosa.codegraph.model.Application application = converter.fromNode(node);

		assertThat(application.getName(), is(applicationName));
		assertThat(application.getModules().size(), is(2));
	}

}