package com.dnfeitosa.codegraph.server.api.controllers;

import com.dnfeitosa.codegraph.server.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
