import React from 'react';

import { Link } from 'react-router-dom';

export default class ArtifactVersions extends React.Component {
    render() {
        const { artifact, versions } = this.props;

        if (!versions || !artifact) {
            return null;
        }

        return (
            <div className="cgr-section cgr-section-split cgr-artifact-versions">
                <h4>Versions</h4>
                <span>
                {versions.map((version) => (
                    <span key={version.version}>
                        <Link to={`/artifacts/${artifact.organization}/${artifact.name}/${version.version}`}>{version.version}</Link>&nbsp;&nbsp;&nbsp;
                    </span>
                ))}
                </span>
            </div>
        );
    }
}
