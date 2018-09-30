import React from 'react';

import apiService from '../../services/api-service';

import ArtifactTitle from './artifact-title';
import ArtifactVersions from './artifact-versions';
import ArtifactDetails from './artifact-details';

export default class ArtifactOverview extends React.Component {

    constructor() {
        super();
        this.state = {};
    }

    componentDidMount() {
        const { artifact } = this.props;

        Promise.all([
            apiService.getArtifactVersions(artifact),
            artifact.version && apiService.getArtifact(artifact).then((data) => data),
        ]).then(([{versions, artifact}, details]) => {
            this.setState({ versions, artifact, details });
        });
    }

    render() {
        const { artifact, versions, details } = this.state;

        return (
            <React.Fragment>
                <ArtifactTitle artifact={this.props.artifact} />
                <div className="">
                    <ArtifactVersions artifact={this.props.artifact} versions={versions} />
                    <ArtifactDetails artifact={details} />
                </div>
            </React.Fragment>
        )
    }
}
