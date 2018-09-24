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
