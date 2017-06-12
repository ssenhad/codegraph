package com.dnfeitosa.codegraph.server.api.resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ArtifactResourceTest {

    @Test
    public void shouldReturnTheResourceUriWhenItContainsAnId() {
        ArtifactResource resource = new ArtifactResource();
        resource.setName("codegraph-server");
        resource.setOrganization("com.dnfeitosa.codegraph");
        resource.setVersion("1.0");

        assertThat(resource.getUri(), is("/api/artifacts/com.dnfeitosa.codegraph/codegraph-server/1.0"));
    }

    @Test
    public void resourceUriIsNullWhenItDoesNotContainAName() {
        assertNull(new ArtifactResource().getUri());
    }
}
