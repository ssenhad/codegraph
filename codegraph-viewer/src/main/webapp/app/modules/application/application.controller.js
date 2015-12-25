'use strict';
var _graph, _data;

function Node(name) {
    this.name = name;
}

function Edge(start, end) {
    this.start = start;
    this.end = end;
}

function Graph(nodes, edges) {
    this.nodes = nodes;
    this.edges = edges;
}

Graph.prototype.asGraphlibGraph = function (nodeFilter, edgeFilter) {
    var graph = new dagreD3.graphlib.Graph()
        .setGraph({})
        .setDefaultEdgeLabel(function () { return {}; });

    this.nodes.filter(nodeFilter).forEach(function (node) {
        graph.setNode(node.name, { label: node.name });
    });
    this.edges.filter(edgeFilter).forEach(function (edge) {
        graph.setEdge(edge.startNode.name, edge.endNode.name);
    });

    return graph;
};

Graph.prototype.organizations = function () {
    return this.edges.map(function (edge) {
        return [ edge.startNode.organization, edge.endNode.organization ];
    }).flatten().distinct();
};

Graph.fromData = function (data) {
    var nodes = data.edges.map(function (edge) {
        return [edge.startNode, edge.endNode]
    }).flatten().distinct();
    return new Graph(nodes, data.edges);
}


angular.module('CodeGraph.application')
    .controller('ApplicationController', function ($scope, $routeParams, api) {
        function f(value) {
            if (value === undefined) {
                return function () {};
            }
            return function () { return value; }
        }

        var hiddenOrgs = [];
        $scope.hiddenOrgs = hiddenOrgs;
        $scope.toggleView = function (organization) {
            console.log(organization);
            if (hiddenOrgs.contains(organization)) {
                hiddenOrgs.remove(organization);
            } else {
                hiddenOrgs.push(organization);
            }
            $scope.render();
        };
        var nodeFilterByOrg = function (node) {
            return !hiddenOrgs.contains(node.organization);
        };

        var edgeFilterByOrg = function (edge) {
            return !hiddenOrgs.contains(edge.startNode.organization) && !hiddenOrgs.contains(edge.endNode.organization);
        };

        // Set up zoom support
        var svg = d3.select('svg'),
            svgGroup = svg.append('g');
        var zoom = d3.behavior.zoom().on("zoom", function() {
            svgGroup.attr("transform", "translate(" + d3.event.translate + ")" + "scale(" + d3.event.scale + ")");
        });
        svg.call(zoom);

        var render = new dagreD3.render();
        $scope.render = function () {
            var graphlib = $scope.graph.asGraphlibGraph(nodeFilterByOrg, edgeFilterByOrg);
            render(d3.select('svg g'), graphlib);
        };

        $scope.showGraph = function (applicationName, moduleName) {
            $scope.requesting = true;
            $scope.rendered = false;
            $scope.graph = {};
            api.dependencyGraph(applicationName, moduleName).then(function (data) {
                $scope.graph = Graph.fromData(data);

                $scope.render();

                $scope.requesting = false;
                $scope.rendered = true;
            });
        };

        api.modules($routeParams.application).then(function (application) {
            $scope.application = application;
        });
    });