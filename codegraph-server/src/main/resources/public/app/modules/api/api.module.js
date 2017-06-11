'use strict';

angular.module('Codegraph.api', [])
    .config(function ($httpProvider) {
        $httpProvider.defaults.headers = {
            'post': { 'Content-Type': 'application/json' },
            'put':  { 'Content-Type': 'application/json' }
        }
    });
