/**
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
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
package co.degraph.server.api.resources;

import java.util.Set;

public class GraphResource {

    private final NodeResource root;
    private final Set<NodeResource> nodes;
    private final Set<EdgeResource> edges;

    public GraphResource(NodeResource root, Set<NodeResource> nodes, Set<EdgeResource> edges) {
        this.root = root;
        this.nodes = nodes;
        this.edges = edges;
    }

    public NodeResource getRoot() {
        return root;
    }

    public Set<EdgeResource> getEdges() {
        return edges;
    }

    public Set<NodeResource> getNodes() {
        return nodes;
    }
}
