'use strict';

angular
    .module('Codegraph', [

        'ngRoute',
        'ui.bootstrap',
        'Codegraph.api',
        'Codegraph.index',
        'Codegraph.artifact',

    ]).config(function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'app/modules/index/partials/index.html',
            controller: 'IndexController'
        });

        $routeProvider.when('/:organization/:name/:version', {
            templateUrl: 'app/modules/artifact/partials/artifactView.html',
            controller: 'ArtifactController'
        });
    });

