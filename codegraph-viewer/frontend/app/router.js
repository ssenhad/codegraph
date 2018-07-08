const angular = require('angular');
require('angular-ui-router');
require('angular-ui-bootstrap');
require('jquery');
require('jstree');
const ngJsTree = require('ng-js-tree');
const cytoscape = require('cytoscape');
require('cytoscape-dagre');
require('cytoscape-navigator');

require('./modules/api/api.module');
require('./modules/api/api.service');
require('./modules/index/index.module');
require('./modules/index/index.service');
require('./modules/index/index.controller');
require('./modules/artifact/artifact.module');
require('./modules/artifact/artifact.module');
require('./modules/artifacts/artifacts.module');
require('./modules/artifacts/artifacts.tree.controller');
require('./modules/artifacts/artifacts.artifact.controller');
require('./modules/artifacts/artifacts.details.controller');
require('./modules/viewer/viewer.module');
require('./modules/viewer/viewer.controller');

angular
    .module('Codegraph', [
        'ui.router',
        'ngJsTree',
        'ui.bootstrap',
        'Codegraph.api',
        'Codegraph.artifact',
        'Codegraph.artifacts',
        'Codegraph.index',
        'Codegraph.viewer',

    ]).run(function ($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
})
    .config(function ($stateProvider) {
        $stateProvider.state('artifacts', {
            abstract: true,
            url: '/artifacts',
            template: require('./modules/artifacts/partials/artifacts.html'),
            controller: 'TreeController',
        }).state('artifacts.start', {
            url: '',
            controller: 'TreeController',
        }).state('artifacts.artifact', {
            url: '/{organization}/{name}',
            views: {
                artifact: {
                    template: require('./modules/artifacts/partials/artifacts.artifact.html'),
                    controller: 'ArtifactController',
                }
            }
        }).state('artifacts.artifact.details', {
            url: '/{version}',
            views: {
                details: {
                    template: require('./modules/artifacts/partials/artifacts.details.html'),
                    controller: 'ArtifactDetailsController',
                }
            }
        });
        $stateProvider.state('viewer', {
            url: '/viewer/{organization}/{name}/{version}/dependency-graph',
            template: require('./modules/viewer/partials/viewer.html'),
            controller: 'ViewerController'
        });
        /*
        $routeProvider.when('/', {
            templateUrl: 'app/modules/index/partials/index.html',
            controller: 'IndexController'
        });

        $routeProvider.when('/artifacts', {
            templateUrl: 'app/modules/artifacts/partials/artifacts.html',
            controller: 'ArtifactsController'
        });

        $routeProvider.when('/:organization/:name/:version/dependencies', {
            templateUrl: 'app/modules/viewer/partials/viewer.html',
            controller: 'ViewerController'
        });

        $routeProvider.when('/artifacts/:organization/:name', {
            templateUrl: 'app/modules/artifacts/partials/artifacts.html',
            controller: 'ArtifactsController'
        });
        */
    });

