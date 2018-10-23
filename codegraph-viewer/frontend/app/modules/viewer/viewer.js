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

import Sidebar from '../../components/page/sidebar';
import Main from '../../components/page/main';
import Container from '../../components/page/container';

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

    // var xCenterOffset = (svg.node().getBoundingClientRect().width - graphlib.graph().width) / 2;
    // svgGroup.attr("transform", "translate(" + xCenterOffset + ", 20)");
    // svg.attr("height", graphlib.graph().height + 40);
}

class ArtifactView {

    dependenciesConfigurations() {
        return ['compile', 'runtime', 'testCompile'];
    }
}

class DisplayOptions extends React.Component {

    constructor() {
        super();
        this.toggle = this.toggle.bind(this);
        this.state = { expanded: false };
    }

    toggle() {
        this.setState({ expanded: !this.state.expanded })
    }

    render() {
        const { expanded } = this.state;
        const icon = expanded ? 'fa-angle-down' : 'fa-angle-up';

        return (
            <div className="mt-auto display-options">
                <div className="head shadow-sm p-1" onClick={this.toggle}>
                    <i className={`px-2 fa ${icon}`} /><span className="h6">Display options</span>
                </div>
                <div className={`body ${ expanded && 'expanded p-2'}`}>
                    <div><b>Node labels</b></div>
                    <div>
                        <div><input type="checkbox" defaultChecked={true}></input> Dependencies</div>
                        <div><input type="checkbox"></input> Types</div>
                        <div><input type="checkbox"></input> Fields</div>
                        <div><input type="checkbox"></input> Methods</div>
                    </div>
                </div>
            </div>
        );
    }
}

class Configurations extends React.Component {
    render() {
        return (
            <Sidebar.Section>
                <div className="h6">Configurations</div>
                {this.props.view.dependenciesConfigurations().map((configuration) => (
                    <span href="#" className="badge badge-pill badge-primary" key={configuration}>{configuration}</span>
                ))}
                <span href="#" className="badge badge-pill badge-secondary">testRuntime</span>
            </Sidebar.Section>
        );
    }
}

class Viewer extends React.Component {

    constructor() {
        super();
        this.state = { view: new ArtifactView() };
    }

    componentDidMount() {
        const { params: artifact } = this.props.match;
        apiService.dependencyGraph(artifact).then((data) => {
            const graph = toGraph(data);
            renderGraph(graph);
            this.setState({ view: new ArtifactView() });
        });
    }

    render() {
        const { params: artifact } = this.props.match;
        const { view } = this.state;
        console.log(artifact);

        return (
            <Container>
                <Sidebar title={`${artifact.organization}:${artifact.name}:${artifact.version}`}>
                    <Configurations view={view} />
                    <DisplayOptions />
                </Sidebar>
                <Main>
                    <div className="w-100 h-100">
                        <svg className="view"></svg>
                    </div>
                </Main>
            </Container>
        );
    }
}

export default withRouter(Viewer);
