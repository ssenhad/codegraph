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
'use strict';

import { Artifact, Node, Edge, Dependency } from '../../models/artifact';

angular.module('Codegraph.viewer')
    .controller('ViewerController', function ($scope, $state, $stateParams, $location, api) {
        $scope.artifact = {
            organization: $stateParams.organization,
            name: $stateParams.name,
            version: $stateParams.version
        };

        function stylesheet() {
            return cytoscape.stylesheet()
                .selector('node')
                .css({
                    'shape': 'ellipse',
                    'width': 'mapData(name.length, 1, 50, 80, 200)',
                    'content': 'data(name)',
                    'text-valign': 'center',
                    'color': 'black',
                    'background-color': '#e7e7e7',
                    'border-width': '0.5',
                    'border-color': '#000',
                    'text-outline-color': '#999',
                    'font-size': '0.7em'
                })
                .selector('node.collapsed')
                .css({
                    'shape': 'ellipse',
                    'width': 10
                })
                .selector('$node > node')
                .css({
                    'background-color': 'red'
                })
                .selector('edge')
                .css({
                    'curve-style': 'bezier',
                    'target-arrow-shape': 'triangle',
                    'target-arrow-color': '#000',
                    'arrow-scale': 0.6,
                    'line-color': '#000',
                    'width': 0.5
                })
                .selector(':selected')
                .css({
                    'background-color': 'red',
                    'line-color': 'black',
                    'target-arrow-color': 'black',
                    'source-arrow-color': 'black'
                })
                .selector('.faded')
                .css({
                    'opacity': 0.25,
                    'text-opacity': 0
                });
        }

        function toArtifact(data) {
            return new Artifact(data.id, data.name, data.organization, data.version, [])
        }

        function toGraph(data) {
            const nodes = data.nodes
                .map(toArtifact)
                .map((node) => {
                return { data: node };
            });

            nodes.forEach((artifact) => {
                artifacts[artifact.data.id] = new Node(artifact.data);
            });

            const edges = data.edges.map(function (edge) {
                return { data: new Edge(artifacts[edge.start.id], new Dependency(artifacts[edge.end.id]), edge.attributes.configurations) };
            });

            return { nodes: nodes, edges: edges };
        }

        var artifacts = {};

        function render(graph) {

            let cy = cytoscape({
                container: document.querySelector('#viewarea'),

                boxSelectionEnabled: true,
                autounselectify: true,
                minZoom: 0.5,
                maxZoom: 3,
                hideEdgesOnViewport: true,

                style: stylesheet(),

                elements: graph,

                layout: {
                    name: 'dagre',
                    padding: 1
                }
            });
            // cy.$('node').qtip({
            //     content: function () {
            //         let value = this.data();
            //         if (value instanceof Dependency) {
            //             return `
            //                 <div>
            //                     <div>Declared: ${value.artifact.version}</div>
            //                 </div>
            //             `;
            //         }
            //         if (value instanceof Artifact) {
            //             return `
            //                 <div>
            //                     <div>Version: ${value.version}</div>
            //                 </div>
            //             `;
            //         }
            //     },
            //     position: {
            //         my: 'top center',
            //         at: 'bottom center'
            //     },
            //     show: {
            //         event: 'mouseover'
            //     },
            //     hide: {
            //         event: 'mouseout'
            //     },
            //     style: {
            //         classes: 'qtip-bootstrap',
            //         tip: {
            //             width: 16,
            //             height: 8
            //         }
            //     }
            // });

            cy.navigator();
        }

        api.dependencyGraph($scope.artifact).then((data) => {
            const graph = toGraph(data);
            render(graph);
        });
    });
