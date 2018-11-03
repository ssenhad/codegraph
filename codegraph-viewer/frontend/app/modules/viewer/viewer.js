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
import * as d3 from 'd3v4';
import * as dagreD3 from 'dagre-d3';
import { withRouter } from 'react-router-dom';

import Sidebar from '../../components/page/sidebar';
import Main from '../../components/page/main';
import Container from '../../components/page/container';

import apiService from '../../services/api-service';
import { Graph } from "../../models/artifact";

Graph.fromData = function (data) {
    return new Graph(data.nodes, data.edges);
};

function asGraphlibGraph(g) {
    var graph = new dagreD3.graphlib.Graph({ compound: true })
        .setGraph({})
        .setDefaultEdgeLabel(function () { return {}; });

    g.nodes.forEach(function (node) {
        graph.setNode(node.id, { label: node.name });
    });
    g.edges.forEach(function (edge) {
        graph.setEdge(edge.start.id, edge.end.id, { label: "", curve: d3.curveBasis, _data: edge });
    });

    return graph;
};

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

    // color nodes based on confs
    svgGroup.selectAll('.node').attr('class', function (node) {
        let classes = d3.select(this).attr('class').split(' ');

        let inEdges = graphlib.inEdges(node);
        if (inEdges.length == 0) {
            return classes.join(' ');
        }
        const configsToNode = inEdges.map((e) => {
            return graphlib.edge(e)._data.attributes.configurations;
        }).flat().distinct();
        classes.push(configsToNode.length == 0 || configsToNode.indexOf('compile') !== -1 ? 'bg-color-1' : 'bg-color-2');
        classes.push('light');
        return classes.join(' ');
    }, true);

    svgGroup.selectAll('.edgePath').attr('class', function (e) {
        let classes = d3.select(this).attr('class').split(' ');

        let edge = graphlib.edge(e);
        if (edge._data.attributes.configurations.indexOf('compile') !== -1) {
            classes.push('color-1');
        } else {
            classes.push('color-2');
        }
        classes.push('light');

        return classes.join(' ');
    });

    // var xCenterOffset = (svg.node().getBoundingClientRect().width - graphlib.graph().width) / 2;
    // svgGroup.attr("transform", "translate(" + xCenterOffset + ", 20)");
    // svg.attr("height", graphlib.graph().height + 40);
}

class ArtifactView {

    constructor(graph) {
        this.graph = graph;
    }

    dependenciesConfigurations() {
        return this.graph.edges
            .map((edge) => edge.attributes.configurations)
            .flat()
            .distinct();
    }

    organizations() {
        return this.graph.nodes
            .map((node) => node.organization)
            .flat()
            .distinct()
            .sort();
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

        d3.schemeCategory20

        return (
            <div className="mt-auto display-options">
                <div className="head shadow-sm p-1" onClick={this.toggle}>
                    <i className={`px-2 fa ${icon}`} /><span className="h6">Display options</span>
                </div>
                <div className={`body ${ expanded && 'expanded p-2'}`}>
                    <div><b>Node labels</b></div>
                    <div>
                        <div><input type="checkbox" defaultChecked={true}></input> Dependencies</div>
                        {/*<div><input type="checkbox"></input> Types</div>*/}
                        {/*<div><input type="checkbox"></input> Fields</div>*/}
                        {/*<div><input type="checkbox"></input> Methods</div>*/}
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
                {this.props.view.dependenciesConfigurations().map((configuration, i) => (
                    <a href="#" className={`px-2 mx-1 badge badge-pill badge-color-${i+1}`} key={configuration}>{configuration}</a>
                ))}
            </Sidebar.Section>
        );
    }
}


class Organizations extends React.Component {
    render() {
        const { view } = this.props;

        return (
            <Sidebar.Section>
                <div className="h6">Organizations</div>
                {view.organizations().map((organization) => (
                    <div key={organization}><i className="fa fa-eye" />{organization}</div>
                ))}
            </Sidebar.Section>
        );
    }
}

class Viewer extends React.Component {

    constructor() {
        super();
        this.state = { view: new ArtifactView({ nodes: [], edges: [] }) };
    }

    componentDidMount() {
        const { params: artifact } = this.props.match;
        apiService.dependencyGraph(artifact).then((data) => {
            let graph = Graph.fromData(data);
            const graphlib = asGraphlibGraph(graph);
            renderGraph(graphlib);
            this.setState({ view: new ArtifactView(graph) });
        });
    }

    render() {
        const { params: artifact } = this.props.match;
        const { view } = this.state;

        return (
            <Container>
                <Sidebar title={`${artifact.organization}:${artifact.name}:${artifact.version}`}>
                    <Configurations view={view} />
                    <Organizations view={view} />
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
