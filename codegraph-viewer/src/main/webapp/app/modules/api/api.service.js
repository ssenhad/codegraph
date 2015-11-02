'use strict';

angular.module('CodeGraph.api')
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

        this.applications = function () {
            return this.get('/api/applications');
        }
    });