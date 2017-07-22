package com.dnfeitosa.codegraph.server.api.controllers;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.server.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtifactController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactController.class);

    private final ArtifactService artifactService;
    private final ArtifactResourceConverter artifactResourceConverter;

    @Autowired
    public ArtifactController(ArtifactService artifactService, ArtifactResourceConverter artifactResourceConverter) {
        this.artifactService = artifactService;
        this.artifactResourceConverter = artifactResourceConverter;
    }

    @RequestMapping(path = "/api/artifact/{organization}/{name}/{version}")
    public ResponseEntity<ArtifactResource> getArtifact(@PathVariable("organization") String organization,
                                                        @PathVariable("name") String name,
                                                        @PathVariable("version") String version) {

        Artifact artifact = artifactService.load(organization, name, version);
        if (artifact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArtifactResource artifactResource = artifactResourceConverter.toResource(artifact);
        return new ResponseEntity<>(artifactResource, HttpStatus.OK);
    }

}
