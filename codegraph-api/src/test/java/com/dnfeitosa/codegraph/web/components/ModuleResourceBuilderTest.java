package com.dnfeitosa.codegraph.web.components;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ModuleResourceBuilderTest {

    private final String applicationName = "applicationName";
    private final Application application = new Application(applicationName);

    private final String name = "name";
    private final String organization = "organization";
    private final String version = "version";
    private final Module module = new Module(name, organization, version, emptyList(), emptySet()) {{
        setApplication(application);
    }};

    private ModuleResourceBuilder resourceBuilder;

    @Before
    public void setUp() {
        resourceBuilder = new ModuleResourceBuilder();
    }

    @Test
    public void shouldConvertAModuleToAModuleResource() {
        ModuleResource resource = resourceBuilder.toResource(module);

        assertBasicModuleValues(resource);
        assertNotNull(resource.getParent());
        assertThat(resource.getParent().getName(), is(applicationName));
    }

    @Test
    public void shouldCreateASimplifiedModuleResourceVersionWithoutTheParent() {
        ModuleResource resource = resourceBuilder.toResource(module, applicationName);

        assertBasicModuleValues(resource);
        assertNull(resource.getParent());
    }

    private void assertBasicModuleValues(ModuleResource resource) {
        assertThat(resource.getName(), is(name));
        assertThat(resource.getOrganization(), is(organization));
        assertThat(resource.getVersion(), is(version));
    }

}