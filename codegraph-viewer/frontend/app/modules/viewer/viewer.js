/*
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
import React from 'react';

import { withRouter } from 'react-router-dom';

import Sidebar from '../../components/sidebar';
import Contents from '../../components/contents';
import Container from '../../components/container';

import apiService from '../../services/api-service';

import * as d3 from 'd3v4';

import * as dagreD3 from 'dagre-d3';

import { Graph, Artifact, Dependency, Edge, Node } from "../../models/artifact";

Graph.fromData = function (data) {
    return new Graph(data.nodes, data.edges);
};

function asGraphlibGraph(g) {
    var graph = new dagreD3.graphlib.Graph()
        .setGraph({})
        .setDefaultEdgeLabel(function () { return {}; });

    console.log(g);
    g.nodes.forEach(function (node) {
        graph.setNode(node.id, { label: node.name });
    });
    g.edges.forEach(function (edge) {
        graph.setEdge(edge.start.id, edge.end.id, { label: "", curve: d3.curveBasis });
    });

    return graph;
};

function toGraph(data) {
    return asGraphlibGraph(Graph.fromData(data));
}

function renderGraph(graphlib) {
    // Set up zoom support
    var svg = d3.select('svg'),
        svgGroup = svg.append('g');
    var zoom = d3.zoom().on("zoom", function() {
        svgGroup.attr("transform", d3.event.transform);
    });
    svg.call(zoom);


    var render = new dagreD3.render();
    render(d3.select('svg g'), graphlib);
    var xCenterOffset = (svg.node().getBoundingClientRect().width - graphlib.graph().width) / 2;
    svgGroup.attr("transform", "translate(" + xCenterOffset + ", 20)");
    svg.attr("height", graphlib.graph().height + 40);
}


class Viewer extends React.Component {

    componentDidMount() {
        const { params: artifact } = this.props.match;
        apiService.dependencyGraph(artifact).then((data) => {
            const graph = toGraph(data);
            renderGraph(graph);
        });
    }

    render() {
        const { params: artifact } = this.props.match;
        return (
            <Container>
                <Sidebar title="Viewer">
                    {artifact.organization}:{artifact.name}:{artifact.version}
                </Sidebar>
                <Contents>
                    <div id="viewarea">
                        <svg></svg>
                    </div>

                </Contents>
            </Container>
        );
    }
}

export default withRouter(Viewer);
