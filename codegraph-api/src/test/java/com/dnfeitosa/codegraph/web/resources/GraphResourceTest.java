package com.dnfeitosa.codegraph.web.resources;

import org.junit.Test;

import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GraphResourceTest {

    @Test
    public void graphUriIsACombinationOfItsRootUriAndName() {
        ApplicationResource application = new ApplicationResource("app-name");
        ModuleResource moduleResource = new ModuleResource(application, "module-name");
        GraphResource<ModuleResource> graphResource = new GraphResource<>(moduleResource, emptySet(), "graph-name");

        assertThat(graphResource.getUri(), is("/applications/app-name/modules/module-name/graph-name"));
    }

}