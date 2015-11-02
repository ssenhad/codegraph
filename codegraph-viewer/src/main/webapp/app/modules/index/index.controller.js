'use strict';

angular.module('CodeGraph.index')
    .controller('IndexController', function ($scope, api) {
        api.applications().then(function (applications) {
            $scope.applications = applications;
        })
    });