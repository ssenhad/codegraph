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

import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.DependencyResource;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@Controller
public class DependenciesController {

    private ArtifactService artifactService;

    @Autowired
    public DependenciesController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/artifacts/{organization}/{name}/{version}/dependency-graph")
    public ResponseEntity<DependencyGraphResource> getDependencyGraph(@PathVariable("organization") String organization,
                                                                      @PathVariable("name") String name,
                                                                      @PathVariable("version") String version) {
        artifactService.loadDependencyGraph(organization, name, version);
        return null;
    }

    public static class DependencyGraphResource {
        private ArtifactResource root;
        private Set<EdgeResource> edges;
    }

    public static class EdgeResource {
        private ArtifactResource start;
        private Set<DependencyResource> targets;
    }
}
