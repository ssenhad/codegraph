import React from 'react';

import { Link } from 'react-router-dom';
import { Section } from "../../components/content";

export default class ArtifactVersions extends React.Component {
    render() {
        const { artifact, versions } = this.props;

        if (!versions || !artifact) {
            return null;
        }

        return (
            <React.Fragment>
                <Section header="Versions">
                    <div>
                    {versions.map((version) => (
                        <span key={version.version}>
                            <Link to={`/artifacts/${artifact.organization}/${artifact.name}/${version.version}`}>{version.version}</Link>&nbsp;&nbsp;&nbsp;
                        </span>
                    ))}
                    </div>
                </Section>
            </React.Fragment>
        );
    }
}
