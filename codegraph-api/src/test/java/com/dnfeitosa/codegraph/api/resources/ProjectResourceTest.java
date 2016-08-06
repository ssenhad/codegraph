package com.dnfeitosa.codegraph.api.resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ProjectResourceTest {

    @Test
    public void shouldReturnTheResourceUriWhenItContainsAnId() {
        ProjectResource resource = new ProjectResource();
        resource.setId(10L);

        assertThat(resource.getUri(), is("/api/projects/10"));
    }

    @Test
    public void resourceUriIsNullWhenItDoesNotContainAnId() {
        assertNull(new ProjectResource().getUri());
    }
}
