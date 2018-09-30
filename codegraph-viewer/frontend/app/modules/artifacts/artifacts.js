import React from 'react';
import { withRouter } from 'react-router-dom';

import Sidebar from '../../components/sidebar';
import Page from '../../components/page';
import Contents from '../../components/contents';

import ArtifactsTree from './artifacts-tree';
import ArtifactOverview from './artifact-overview'

class Artifacts extends React.Component {

    constructor(props) {
        super(props);

        this.select = this.select.bind(this);
        this.state = {};
    }

    static getDerivedStateFromProps(props) {
        const { organization, name, version } = props.match.params;
        return {
            artifact: {
                organization,
                name,
                version
            }
        }
    }

    render() {
        const { artifact } = this.state;
        return (
            <Page>
                <Sidebar title="Artifacts">
                    <ArtifactsTree onSelect={this.select} />
                </Sidebar>
                <Contents>
                    <ArtifactOverview artifact={artifact} key={`${artifact.organization}:${artifact.name}:${artifact.version}`}/>
                </Contents>
            </Page>
        );
    }

    select(artifact) {
        if (artifact.type === 'organization') {
            return;
        }

        this.props.history.push(`/artifacts/${artifact.parent}/${artifact.name}`);
    }
}

export default withRouter(Artifacts);
