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
package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Graph;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.server.api.controllers.ArtifactNodeResource;
import com.dnfeitosa.codegraph.server.api.controllers.EdgeResource;
import com.dnfeitosa.codegraph.server.api.controllers.GraphResource;
import com.dnfeitosa.codegraph.server.api.controllers.NodeReference;
import com.dnfeitosa.codegraph.server.api.controllers.NodeResource;
import com.dnfeitosa.codegraph.server.services.DependencyEdge;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
public class GraphResourceConverter {

    public GraphResource toResource(Graph<Artifact, DependencyEdge> dependencyGraph) {
        ArtifactNodeResource root = toArtifactNode(dependencyGraph.getRoot());
        Set<NodeResource> nodes = dependencyGraph.getNodes().stream().map(this::toArtifactNode).collect(toSet());
        Set<EdgeResource> edges = dependencyGraph.getEdges().stream().map(this::toEdgeResource).collect(toSet());
        return new GraphResource(root, nodes, edges);
    }

    private EdgeResource toEdgeResource(DependencyEdge edge) {
        NodeReference start = new NodeReference(edge.getStartId(), "artifact");
        NodeReference end = new NodeReference(edge.getEndId(), "artifact");
        return new EdgeResource(start, end, edge.getConfigurations());
    }

    private ArtifactNodeResource toArtifactNode(Artifact artifact) {
        String id = artifact.getId();
        String organization = artifact.getOrganization();
        String name = artifact.getName();
        Version version = artifact.getVersion();
        return new ArtifactNodeResource(id, organization, name, version.getNumber());
    }
}
