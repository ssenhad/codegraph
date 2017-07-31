'use strict';
angular.module('Codegraph.artifact')
    .controller('ArtifactController', function ($scope, $routeParams, api) {
        let artifact = {
            organization: $routeParams.organization,
            name: $routeParams.name,
            version: $routeParams.version
        };

        api.getVersions(artifact).then(function (data) {
            $scope.availableVersions = data.versions;
        });

        api.getArtifact(artifact).then(function (data) {
            $scope.artifact = data.artifact;
            $scope.stats = {
                classes: 1234,
                dependencies: data.artifact.dependencies.length,
                dependents: 0
            };
        });
    });
