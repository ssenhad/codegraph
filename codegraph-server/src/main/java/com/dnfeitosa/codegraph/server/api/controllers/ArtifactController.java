package com.dnfeitosa.codegraph.server.api.controllers;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.server.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.server.api.services.ArtifactService;
import com.dnfeitosa.codegraph.server.api.services.ItemDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(value = "/artifacts", method = GET)
    public ResponseEntity<ArtifactsResource> getArtifacts() {
        List<Artifact> artifacts = artifactService.loadAll();
        return new ResponseEntity<>(artifactResourceConverter.toResources(artifacts), HttpStatus.OK);
    }

    @RequestMapping(value = "/artifacts", method = POST)
    public ResponseEntity<ArtifactResource> addArtifact(@RequestBody ArtifactResource artifactResource) {
//        Artifact artifact = artifactResourceConverter.toModel(artifactResource);
//        Artifact created = artifactService.addArtifact(artifact);
//        return new ResponseEntity<>(artifactResourceConverter.toResource(created), HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/artifacts/{id}")
    public ResponseEntity<ArtifactResource> getArtifact(@PathVariable("id") Long id) {
        try {
            Artifact artifact = artifactService.loadArtifact(id);

            return new ResponseEntity<>(artifactResourceConverter.toResource(artifact), HttpStatus.OK);
        } catch (ItemDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
