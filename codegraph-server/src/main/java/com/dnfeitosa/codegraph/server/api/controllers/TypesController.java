package com.dnfeitosa.codegraph.server.api.controllers;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.server.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.server.api.converters.TypeResourceConverter;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.TypesResource;
import com.dnfeitosa.codegraph.server.api.services.ArtifactService;
import com.dnfeitosa.codegraph.server.api.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class TypesController {

    private TypeService typeService;
    private ArtifactService artifactService;
    private TypeResourceConverter typesConverter;
    private ArtifactResourceConverter artifactResourceConverter;

    @Autowired
    public TypesController(TypeService typeService, ArtifactService artifactService, TypeResourceConverter typesConverter,
                           ArtifactResourceConverter artifactResourceConverter) {
        this.typeService = typeService;
        this.artifactService = artifactService;
        this.typesConverter = typesConverter;
        this.artifactResourceConverter = artifactResourceConverter;
    }

    @RequestMapping(value = "/artifacts/{id}/types")
    public ResponseEntity<TypesResource> getTypes(@PathVariable("id") Long artifactId) {
        ArtifactResource artifactResource = getArtifactResource(artifactId);
        Set<Type> types = typeService.getTypesFromArtifact(artifactId);
        TypesResource typesResource = typesConverter.toResource(artifactResource, types);
        return new ResponseEntity<>(typesResource, HttpStatus.OK);
    }

    private ArtifactResource getArtifactResource(Long artifactId) {
        Artifact artifact = artifactService.loadArtifact(artifactId);
        return artifactResourceConverter.toResource(artifact);
    }
}
