import React from 'react';

import apiService from '../../services/api-service';

import ArtifactTitle from './artifact-title';
import ArtifactVersions from './artifact-versions';
import ArtifactDetails from './artifact-details';

const ContentsHead = (props) => {
    return (
        <div className={`cgr-contents-head ${props.className}`}>
            {props.children}
        </div>
    );
};

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
        const { versions, details } = this.state;

        return (
            <React.Fragment>
                <ContentsHead className="shadow-sm">
                    <ArtifactTitle artifact={this.props.artifact} />
                    <ArtifactVersions artifact={this.props.artifact} versions={versions} />
                </ContentsHead>
                <ArtifactDetails artifact={details} />
            </React.Fragment>
        )
    }
}
