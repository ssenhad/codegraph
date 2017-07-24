'use strict';
angular.module('Codegraph.artifact')
    .controller('ArtifactController', function ($scope, $routeParams, api) {
        let artifact = {
            organization: $routeParams.organization,
            name: $routeParams.name,
            version: $routeParams.version
        };

        api.getArtifact(artifact).then(function (data) {
            $scope.artifact = data.artifact;
            $scope.allVersions = [
                '1.0', '1.1', '1.2', '1.2.1', '1.2.2', '1.2.3', '2.0-SNAPSHOT', '2.0-RELEASE', '2.1-SNAPSHOT'
            ];
            $scope.stats = {
                classes: 1234,
                dependencies: data.artifact.dependencies.length,
                dependents: 0
            };
        });
    });
