package com.dnfeitosa.codegraph.server.api.resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TypesResourceTest {

    @Test
    public void shouldReturnTheResourceUri() {
        TypesResource typesResource = new TypesResource(new ArtifactResource() {{
            setName("name");
            setOrganization("organization");
            setVersion("version");
        }});

        assertThat(typesResource.getUri(), is("/api/artifacts/organization/name/version/types"));
    }

    @Test
    public void shouldReturnNullWhenParentArtifactDoesNotContainAnUri() {
        TypesResource typesResource = new TypesResource(new ArtifactResource());

        assertNull(typesResource.getUri());
    }
}
