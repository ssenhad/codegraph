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
package co.degraph.server.component.services;

import co.degraph.core.models.Artifact;
import co.degraph.core.models.Edge;
import co.degraph.core.models.Graph;
import co.degraph.core.models.Node;
import co.degraph.server.services.DependencyEdge;
import org.apache.commons.collections4.Predicate;

import java.util.function.Consumer;

import static java.lang.String.format;
import static org.apache.commons.collections4.IterableUtils.find;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class GraphChecks {

    private Graph<Artifact, DependencyEdge> graph;

    public GraphChecks(Graph<Artifact, DependencyEdge> graph) {
        this.graph = graph;
    }

    public GraphChecks hasNodes(int count) {
        assertThat(getValue().getNodes().size(), is(count));
        return this;
    }

    public GraphChecks hasRoot(String id) {
        assertThat(getValue().getRoot().getId(), is(id));
        return this;
    }

    public GraphChecks hasNode(String id) {
        // WARNING: do not inline this variable!
        // https://bugs.openjdk.java.net/browse/JDK-8062253
        Predicate<Artifact> byId = n -> id.equals(n.getId());

        Node<Artifact> node = find(getValue().getNodes(), byId);
        assertNotNull(format("Node %s not found in graph.", id), node);
        return this;
    }

    public GraphChecks hasEdges(int count) {
        assertThat(getValue().getEdges().size(), is(count));
        return this;
    }

    public GraphChecks hasEdge(String startId, String endId, Consumer<?> o) {
        // WARNING: do not inline this variable!
        // https://bugs.openjdk.java.net/browse/JDK-8062253
        Predicate<DependencyEdge> byStartIdAndEndId = e -> {
            return startId.equals(e.getStartId()) && endId.equals(e.getEndId());
        };

        Edge<DependencyEdge> edge = find(getValue().getEdges(), byStartIdAndEndId);
        assertNotNull(format("Edge %s -> %s not found in graph.", startId, endId), edge);
        return this;
    }

    private Graph<Artifact, DependencyEdge> getValue() {
        assertNotNull("Graph is null", graph);
        return graph;
    }
}
