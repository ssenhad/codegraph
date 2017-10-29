/**
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dnfeitosa.codegraph.server.api.controllers;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.server.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.IndexResource;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import com.dnfeitosa.codegraph.server.services.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class IndexController {

    private IndexService indexService;
    private ArtifactResourceConverter artifactConverter;
    private ArtifactService artifactService;

    @Autowired
    public IndexController(IndexService indexService, ArtifactService artifactService, ArtifactResourceConverter artifactConverter) {
        this.indexService = indexService;
        this.artifactService = artifactService;
        this.artifactConverter = artifactConverter;
    }

    @RequestMapping(value = "/api/index", method = POST)
    public ResponseEntity<ArtifactResource> index(@RequestBody IndexResource indexResource) {
        ArtifactResource artifactResource = indexResource(indexResource);

        Artifact loadedArtifact = loadArtifact(artifactResource);
        ArtifactResource createdArtifact = artifactConverter.toResource(loadedArtifact);

        return new ResponseEntity<>(createdArtifact, HttpStatus.CREATED);
    }

    private ArtifactResource indexResource(@RequestBody IndexResource indexResource) {
        ArtifactResource artifactResource = indexResource.getArtifact();
        Artifact artifact = artifactConverter.toModel(artifactResource);

        Set<Artifact> dependencyArtifacts = indexResource.getDependencyArtifacts().stream()
            .map(artifactConverter::toModel)
            .collect(toSet());

        indexService.index(artifact, dependencyArtifacts);
        return artifactResource;
    }

    private Artifact loadArtifact(ArtifactResource artifactResource) {
        String organization = artifactResource.getOrganization();
        String name = artifactResource.getName();
        String version = artifactResource.getVersion();
        return artifactService.load(organization, name, version);
    }
}
