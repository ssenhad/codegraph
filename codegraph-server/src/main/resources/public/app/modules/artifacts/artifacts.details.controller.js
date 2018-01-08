/*
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
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
    .controller('ArtifactDetailsController', function ($scope, $stateParams, $state, api) {
        function displayArtifact(artifact) {
            if (artifact.organization && artifact.name/* && artifact.version*/) {
                api.getArtifact(artifact).then(function (data) {
                    $scope.artifact = data;
                    $scope.stats = {
                        classes: 1234,
                        dependencies: data.dependencies.length,
                        dependents: 0
                    };
                }).catch(function () {
                    $scope.noDetails = true;
                });
            }
        }
        var artifact = {
            organization: $stateParams.organization,
            name: $stateParams.name,
            version: $stateParams.version
        }
        displayArtifact(artifact);
    });
