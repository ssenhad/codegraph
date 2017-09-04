/*
 * Copyright (C) 2015-2017 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
'use strict';

angular.module('Codegraph.artifacts')
    .controller('ArtifactsController', function ($scope, $state, $stateParams, $location, api) {
        function displayArtifact(artifact) {
            console.log('to display', artifact);
            if (artifact.organization && artifact.name/* && artifact.version*/) {
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
            }
        }
        var artifact = {
            organization: $stateParams.organization,
            name: $stateParams.name,
            version: '0.0.1'
        }
        displayArtifact(artifact);
    });
