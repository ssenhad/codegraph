package com.dnfeitosa.codegraph.server.api.resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ArtifactResourceTest {

    @Test
    public void shouldReturnTheResourceUriWhenItContainsAnId() {
        ArtifactResource resource = new ArtifactResource();
        resource.setId(10L);

        assertThat(resource.getUri(), is("/api/artifacts/10"));
    }

    @Test
    public void resourceUriIsNullWhenItDoesNotContainAnId() {
        assertNull(new ArtifactResource().getUri());
    }
}
