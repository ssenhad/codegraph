package com.dnfeitosa.codegraph.web.resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModuleResourceTest {

    @Test
    public void shouldGenerateTheResourceUri() {
        ModuleResource resource = new ModuleResource(new ApplicationResource("application-name"), "module-name");

        assertThat(resource.getUri(), is("/applications/application-name/modules/module-name"));
    }

}