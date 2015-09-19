package com.dnfeitosa.codegraph.core.loaders;

import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.impl.SimpleApplication;
import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationLoaderTest {

	private ApplicationLoader loader;

	@Before
	public void setUp() {
		loader = new ApplicationLoader();
	}

	@Test
	public void shouldLoadAnApplication() {
        SimpleApplication applicationDescriptor = new SimpleApplication("application name", "application location", asList(new ModuleDescriptor() {
            @Override
            public String getName() {
                return "module name";
            }

            @Override
            public String getLocation() {
                return "module location";
            }

            @Override
            public List<Jar> getDependencies() {
                return null;
            }

            @Override
            public Set<ArtifactType> getExportTypes() {
                return null;
            }
        }));

        Application application = loader.load(applicationDescriptor);

		assertThat(application.getName(), is("application name"));
        assertThat(application.getModules().size(), is(1));
        assertThat(application.getModules().get(0).getName(), is("module name"));
    }
}