'use strict';

angular
    .module('CodeGraph', [
        'ngRoute',
        'ui.bootstrap',
        'CodeGraph.api',
        'CodeGraph.index',
        'CodeGraph.application',
    ]).config(function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'app/modules/index/partials/index.html',
            controller: 'IndexController'
        });

        $routeProvider.when('/:application', {
            templateUrl: 'app/modules/application/partials/applicationView.html',
            controller: 'ApplicationController'
        });
    });

