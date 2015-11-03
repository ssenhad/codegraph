'use strict';

angular.module('CodeGraph.application')
    .controller('ApplicationController', function ($scope, $routeParams, api) {
        var uri = '/api/applications/{name}'.apply({name: $routeParams.application});
        api.get(uri).then(function (application) {
            var graph = new dagreD3.graphlib.Graph()
                .setGraph({})
                .setDefaultEdgeLabel(function () {
                    return {};
                });

            application.modules.forEach(function (module, index) {
                graph.setNode(module.name, {label: module.name});
            });

            var render = new dagreD3.render();
            var svg = d3.select('svg'),
                svgGroup = svg.append('g');

            // Set up zoom support
            var zoom = d3.behavior.zoom().on("zoom", function() {
                svgGroup.attr("transform", "translate(" + d3.event.translate + ")" +
                    "scale(" + d3.event.scale + ")");
            });
            svg.call(zoom);
            render(d3.select('svg g'), graph);
        });
    });