'use strict';

angular
    .module('CodeGraph', [
        'ngRoute',
        'ui.bootstrap',
        'CodeGraph.api',
        'CodeGraph.index',

    ]).config(function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'app/modules/index/partials/index.html',
            controller: 'IndexController'
        });
    });

