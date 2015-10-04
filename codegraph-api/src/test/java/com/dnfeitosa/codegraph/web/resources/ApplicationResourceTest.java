package com.dnfeitosa.codegraph.web.resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationResourceTest {

    @Test
    public void shouldGenerateTheResourceUri() {
        ApplicationResource resource = new ApplicationResource("application-name");

        assertThat(resource.getUri(), is("/applications/application-name"));
    }

}