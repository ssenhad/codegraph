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

angular.module('Codegraph.api')
    .service('api', function ($http, $q) {
        this.get = function (url) {
            return $http.get(url)
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

        this.dependencyGraph = function (artifact) {
            return this.get(`/api/artifacts/${artifact.organization}/${artifact.name}/${artifact.version}/dependency-graph`)
        };

        this.getArtifact = function (artifact) {
            return this.get(`/api/artifacts/${artifact.organization}/${artifact.name}/${artifact.version}`)
        };

        this.getVersions = function (organization, name) {
            return this.get(`/api/artifacts/${organization}/${name}/versions`);
        };

        this.getTreeItems = function(parent) {
            return this.get(`/ui/tree/nodes?parent=${parent ? parent : ''}`);
        }
    });
