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
package co.degraph.server.acceptance.api.controllers;

import co.degraph.server.api.resources.EdgeResource;
import co.degraph.server.api.resources.GraphResource;
import co.degraph.server.api.resources.NodeResource;

import java.util.function.Consumer;

import static org.apache.commons.collections4.IterableUtils.find;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class GraphResourceChecks {

    private GraphResource resource;

    public GraphResourceChecks(GraphResource resource) {
        this.resource = resource;
    }

    public GraphResourceChecks hasRoot(String id) {
        assertThat(getValue().getRoot().getId(), is(id));
        return this;
    }

    public GraphResourceChecks hasNodes(int count) {
        assertThat(getValue().getNodes().size(), is(count));
        return this;
    }

    public GraphResourceChecks hasNode(String id, Consumer<NodeResourceChecks> nodeCheck) {
        NodeResource node = find(getValue().getNodes(), n -> id.equals(n.getId()));
        nodeCheck.accept(new NodeResourceChecks(node));
        return this;
    }

    public GraphResourceChecks hasEdges(int count) {
        assertThat(getValue().getEdges().size(), is(count));
        return this;
    }

    public GraphResourceChecks hasEdge(String startId, String endId, Consumer<EdgeResourceChecks> edgeCheck) {
        EdgeResource edge = find(getValue().getEdges(),
            e -> e.getStart().getId().equals(startId)
                && e.getEnd().getId().equals(endId));

        edgeCheck.accept(new EdgeResourceChecks(edge));
        return this;
    }

    private GraphResource getValue() {
        assertNotNull("GraphResource is null", resource);
        return resource;
    }
}
