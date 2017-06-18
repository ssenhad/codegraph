package com.dnfeitosa.codegraph.server.api.controllers;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.server.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.IndexResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class IndexController {

    private IndexService indexService;
    private ArtifactResourceConverter artifactConverter;

    @Autowired
    public IndexController(IndexService indexService, ArtifactResourceConverter artifactConverter) {
        this.indexService = indexService;
        this.artifactConverter = artifactConverter;
    }

    @RequestMapping(value = "/api/index", method = POST)
    public ResponseEntity<ArtifactResource> index(@RequestBody IndexResource indexResource) {
        ArtifactResource resource = indexResource.getArtifact();
        Artifact artifact = artifactConverter.toModel(resource);
        indexService.index(artifact);
        return null;
    }
}
