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
import React from 'react';

import { Treee } from '@dnfeitosa/react-treee';
import { Node } from '@dnfeitosa/react-treee/models';

import apiService from '../../services/api-service';

class ArtifactsTree extends React.Component {

    constructor() {
        super();
        this.state = { artifacts: {} }
    }

    onOpen(node) {
        if (Array.isArray(node.children)) {
            return;
        }
        node.loading = true;
        return apiService.artifactTree(node.id).then((data) => {
            return node.children = data.nodes.map((n) => {
                node.loading = false;
                n.icon = n.type === 'organization' ? 'folder' : 'file';
                let data = Node.fromData(n);
                data.children = n.type === 'organization';
                return data;
            });
        });
    }

    onSelect(artifactNode) {
        this.props.onSelect(artifactNode);
        this.setState({ artifactNode });
    }

    componentDidMount() {
        apiService.artifactTree().then((data) => {
            this.setState({
                artifacts: data.nodes.map((node) => {
                    node.children = true;
                    node.icon = node.type === 'organization' ? 'folder' : 'file';
                    return node;
                })
            });
        });
    }

    render() {
        return (
            <Treee data={this.state.artifacts}
                   onOpenNode={this.onOpen.bind(this)}
                   onSelectNode={this.onSelect.bind(this)} />
        );
    }
}

export default ArtifactsTree;
