'use strict';
angular.module('Codegraph.artifact')
    .controller('ArtifactController', function ($scope, $routeParams, api) {
        $scope.artifact = {
            organization: $routeParams.organization,
            name: $routeParams.name,
            version: $routeParams.version
        };

        function stylesheet() {
            return cytoscape.stylesheet()
                .selector('node')
                .css({
                    'shape': 'ellipse',
                    'width': 'mapData(label.length, 1, 50, 80, 200)',
                    'content': 'data(label)',
                    'text-valign': 'center',
                    'color': 'black',
                    'background-color': '#e7e7e7',
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
                    'target-arrow-color': '#ccc',
                    'arrow-scale': 0.6,
                    'line-color': '#ccc',
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

        function toGraph(artifact) {
            let dependencies = artifact.dependencies;
            let nodes = dependencies.map(function (dep) {
                return { data: new Node(dep) };
            });
            nodes.push({ data: new Node(artifact) });

            let edges = dependencies.map(function (dep) {
                return { data: new Edge(new Node(artifact), new Node(dep)) };
            });

            return { nodes: nodes, edges: edges };
        }

        function renderGraph(artifact) {
            let graph = toGraph(artifact);
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
            cy.$('node').qtip({
                content: function () {
                    let value = this.data('value');
                    if (value instanceof Dependency) {
                        return `
                            <div>
                                <div>Declared: ${value.artifact.version}</div>
                                <div>Resolved: ${value.resolvedVersion}</div>
                            </div>
                        `;
                    }
                    if (value instanceof Artifact) {
                        return `
                            <div>
                                <div>Version: ${value.version}</div>
                            </div>
                        `;
                    }
                },
                position: {
                    my: 'top center',
                    at: 'bottom center'
                },
                show: {
                    event: 'mouseover'
                },
                hide: {
                    event: 'mouseout'
                },
                style: {
                    classes: 'qtip-bootstrap',
                    tip: {
                        width: 16,
                        height: 8
                    }
                }
            });

            cy.navigator();
        }

        $scope.directDependencies = function () {
            function toDependency(dep) {
                let artifact = new Artifact(dep.name, dep.organization, dep.version.declared);
                return new Dependency(artifact, dep.configurations, dep.version.resolved);
            }

            api.directDependencies($scope.artifact).then(function (data) {
                let dependencies = data.artifact.dependencies.map(toDependency);
                let artifact = new Artifact(data.artifact.name, data.artifact.organization, data.artifact.version, dependencies);
                renderGraph(artifact);
            });
        }
    });
