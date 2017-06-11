'use strict';

angular.module('Codegraph.index')
    .controller('IndexController', function ($scope, api) {
        api.artifacts().then(function (response) {
            $scope.artifacts = response.artifacts;
        })
    });
