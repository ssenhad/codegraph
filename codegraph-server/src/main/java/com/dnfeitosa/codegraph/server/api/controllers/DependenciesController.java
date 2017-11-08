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
import com.dnfeitosa.codegraph.core.models.Graph;
import com.dnfeitosa.codegraph.server.api.converters.GraphResourceConverter;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import com.dnfeitosa.codegraph.server.services.DependencyEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DependenciesController {

    private ArtifactService artifactService;
    private GraphResourceConverter graphResourceConverter;

    @Autowired
    public DependenciesController(ArtifactService artifactService, GraphResourceConverter graphResourceConverter) {
        this.artifactService = artifactService;
        this.graphResourceConverter = graphResourceConverter;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/artifacts/{organization}/{name}/{version}/dependency-graph")
    public ResponseEntity<GraphResource> getDependencyGraph(@PathVariable("organization") String organization,
                                                            @PathVariable("name") String name,
                                                            @PathVariable("version") String version) {

        Graph<Artifact, DependencyEdge> dependencyGraph = artifactService.loadDependencyGraph(organization, name, version);
        if (dependencyGraph == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GraphResource graphResource = graphResourceConverter.toResource(dependencyGraph);
        return ResponseEntity.ok(graphResource);
    }

}
