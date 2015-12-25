'use strict';

angular.module('CodeGraph.api')
    .service('api', function ($http, config) {
        this.get = function (url) {
            return $http
                .get([ config.baseUrl(), url ].join('/'))
                .then(function (response) {
                    return response.data;
                });
        };

        this.post = function (url, data) {
            return $http.post(url, data)
                .then(function (response) {
                    return response.data;
                });
        };

        this.put = function (url, data) {
            return $http.put(url, data)
                .then(function (response) {
                    return response.data;
                });
        };

        this.applications = function () {
            return this.get('api/applications');
        };

        this.modules = function (application) {
            return this.get('api/applications/{name}'.apply({ name: application }));
        }

        this.dependencyGraph = function (application, module) {
            return this.get('api/applications/{application}/modules/{module}/dependency-graph'.apply({ application: application, module:module }))
        }
    });