'use strict';

angular.module('Codegraph.api')
    .service('api', function ($http) {
        this.get = function (url) {
            return $http
                .get(url)
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

        this.artifacts = function () {
            return this.get('/api/artifacts');
        };

        this.directDependencies = function (artifact) {
            return this.get(`/api/artifacts/${artifact.organization}/${artifact.name}/${artifact.version}/dependencies`)
        };

        this.dependencyGraph = function (artifact) {
            return this.get(`/api/artifacts/${artifact.organization}/${artifact.name}/${artifact.version}/dependency-graph`)
        };

        this.getArtifact = function (artifact) {
            return this.get(`/api/artifacts/${artifact.organization}/${artifact.name}/${artifact.version}`)
        }
    });
