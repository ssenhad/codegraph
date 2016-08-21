package com.dnfeitosa.codegraph.api.controllers;

import com.dnfeitosa.codegraph.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.api.services.ArtifactService;
import com.dnfeitosa.codegraph.core.models.Artifact;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactControllerTest {

    private long createdId = 10;

    private ArtifactService artifactService;
    private ArtifactResourceConverter artifactResourceConverter;

    @Before
    public void setUp() {
        artifactService = new ArtifactService(null, null) {
            @Override
            public Artifact addArtifact(Artifact artifact) {
                return new Artifact(createdId, null, null, null, null, null);
            }
        };

        artifactResourceConverter = new ArtifactResourceConverter() {
            @Override
            public Artifact toModel(ArtifactResource artifactResource) {
                return new Artifact(null, null, null, null, null);
            }

            @Override
            public ArtifactResource toResource(Artifact artifact) {
                return new ArtifactResource() {{
                    setId(createdId);
                }};
            }
        };
    }

    @Test
    public void shouldCreateANewArtifactAndReturnTheCreatedResource() {
        ArtifactController controller = new ArtifactController(artifactService, artifactResourceConverter);

        ResponseEntity<ArtifactResource> response = controller.addArtifact(new ArtifactResource());

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getId(), is(createdId));
    }

}