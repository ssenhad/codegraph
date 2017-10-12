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
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.server.api.converters.ArtifactResourceConverter;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactVersions;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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

    @RequestMapping(path = "/api/artifacts/{organization}/{name}/{version}")
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

    @RequestMapping(path = "/api/artifacts/{organization}/{name}/versions")
    public ResponseEntity<ArtifactVersions> getVersions(@PathVariable("organization") String organization,
                                                        @PathVariable("name") String name) {
        Set<AvailableVersion> versions = artifactService.getVersions(organization, name);
        ArtifactVersions artifactVersions = artifactResourceConverter.toResource(organization, name, versions);
        return new ResponseEntity<>(artifactVersions, HttpStatus.OK);
    }
}
