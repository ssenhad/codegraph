/*
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either value 3 of the License, or
 * (at your option) any later value.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import * as React from 'react';
import * as d3 from 'd3';
import * as dagre from 'dagre';
import * as dagreD3 from 'dagre-d3';
import * as _ from 'lodash';

import {RouteComponentProps, withRouter} from 'react-router-dom';

import {Sidebar} from '../../components/page/sidebar';

import Main from '../../components/page/main';
import Container from '../../components/page/container';

import apiService from '../../services/api-service';

import {Edge, Graph, Node} from "../../models/graph";
import {ArtifactView} from "./artifactView";
import {Configurations} from "./configurations";
import {ArtifactIdentity} from "../../models/core";


function asGraphlibGraph(g: Graph) : dagre.graphlib.Graph {
    var graph = new dagre.graphlib.Graph({ compound: true })
        .setGraph({})
        .setDefaultEdgeLabel(function () { return {}; });

    g.nodes.forEach(function (node: any) {
        graph.setNode(node.id, { label: node.name });
    });
    g.edges.forEach(function (edge: any) {
        graph.setEdge(edge.start.id, edge.end.id, { label: "", curve: d3.curveBasis, _data: edge });
    });

    return graph;
}

function renderGraph(graphlib: dagre.graphlib.Graph) {
    // Set up zoom support
    var svg = d3.select('svg'),
        svgGroup = svg.append('g');
    var zoom = d3.zoom().on("zoom", function() {
        svgGroup.attr("transform", d3.event.transform);
    });
    svg.call(zoom);

    var render = new dagreD3.render();

    // @ts-ignore
    render(d3.select('svg g'), graphlib);

    // color nodes based on confs
    svgGroup.selectAll('.node').attr('class', function (node: string) {
        let classes = d3.select(this).attr('class').split(' ');

        let inEdges = graphlib.inEdges(node) as Array<dagre.Edge>;
        if (inEdges.length == 0) {
            return classes.join(' ');
        }

        const configsToNode = _.flatMap(inEdges, (e) => {
            return graphlib.edge(e)._data.attributes.configurations;
        });

        classes.push(configsToNode.length == 0 || configsToNode.indexOf('compile') !== -1 ? 'bg-color-1' : 'bg-color-2');
        return classes.join(' ');
    });

    svgGroup.selectAll('.edgePath').attr('class', function (e: dagre.Edge) {
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

interface ViewerProps extends RouteComponentProps<ArtifactIdentity> {
}

type ViewerState = { view: ArtifactView };

class Viewer extends React.Component<ViewerProps, ViewerState> {

    constructor(props: ViewerProps) {
        super(props);
        this.state = { view: new ArtifactView({ nodes: [], edges: [] }) };
        this.toggle = this.toggle.bind(this);
    }

    componentDidMount() {
        const { params: artifact } = this.props.match;
        apiService.dependencyGraph(artifact).then((data) => {
            let graph = Graph.fromData(data);
            const g = asGraphlibGraph(graph);
            renderGraph(g);
            this.setState({ view: new ArtifactView(graph, artifact) });
        });
    }

    toggle(configuration: string) {
        let view = this.state.view;
        let graph = view.graph;

        view.hideNodes((node: Node<any>) => {
            // let attributes = node)._data.attributes;
            // return attributes.length == 1 && attributes.indexOf(configuration) !== -1;
            return false;
        });

        view.hideEdges((edge: Edge) => {
            let configurations = edge.attributes.configurations;
            return configurations.length == 1 && configurations.indexOf(configuration) !== -1;
        });
        const graphlib = asGraphlibGraph(graph);
        renderGraph(graphlib);
    }

    render() {
        const { params: artifact } = this.props.match;
        const { view } = this.state;

        return (
            <Container>
                <Sidebar title={`${artifact.organization}:${artifact.name}:${artifact.version}`}>
                    <Configurations view={view} toggle={this.toggle} />
                    {/*<Organizations view={view} />*/}
                    {/*<DisplayOptions />*/}
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
