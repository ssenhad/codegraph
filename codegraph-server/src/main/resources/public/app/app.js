'use strict';

angular
    .module('Codegraph', [

        'ngRoute',
        'ui.bootstrap',
        'Codegraph.api',
        'Codegraph.artifact',
        'Codegraph.index',
        'Codegraph.viewer',

    ]).config(function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'app/modules/index/partials/index.html',
            controller: 'IndexController'
        });

        $routeProvider.when('/:organization/:name/:version/dependencies', {
            templateUrl: 'app/modules/viewer/partials/viewer.html',
            controller: 'ViewerController'
        });

        $routeProvider.when('/:organization/:name/:version', {
            templateUrl: 'app/modules/artifact/partials/artifact.html',
            controller: 'ArtifactController'
        });
    });

