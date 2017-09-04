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
    .controller('TreeController', function ($scope, $state, $stateParams, $location, api) {
        console.log('tree');
        $scope.treeData = function (node, callback) {
            if (node && callback) {
                api.getTreeItems(node.id).then(function (data) {
                    var nodes = data.nodes.map(function (node) {
                        node.data = node;
                        node.state = {closed: true};
                        node.text = node.name;
                        node.children = node.type === 'organization';
                        return node;
                    });
                    callback.call(this, nodes);
                });
            }
            return [];
        };

        $scope.selectNode = function (element, event) {
            var node = event.node;
            if (node.type !== 'artifact') {
                return;
            }
            var artifact = {
                organization: node.data.parent,
                name: node.data.name,
                version: '0.0.1'
            }
            $state.go('artifacts.artifact', artifact, { location: true });
        };

        $scope.treeConfig = {
            core: {
                multiple: false,
                animation: true,
                error: function (error) {
                    console.log('treeCtrl: error from js tree - ' + angular.toJson(error));
                },
                check_callback: false,
                worker: true,
            },
            types: {
                default: {
                    icon: 'glyphicon glyphicon-folder-close'
                },
                artifact: {
                    icon: 'glyphicon glyphicon-file'
                }
            },
            version: 1,
            plugins: ['types', "wholerow"]
        };
    });
