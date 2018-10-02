import React from 'react';

import { Link } from 'react-router-dom';

import Content, { Section } from '../../components/content';

const DependenciesTable = ({ artifact }) => {
    return (
        <table className="table table-sm table-bordered table-responsive-lg">
            <thead>
                <tr>
                    <th>Organization</th>
                    <th>Name</th>
                    <th>Version</th>
                    <th>Configurations</th>
                </tr>
            </thead>
            <tbody>
                {artifact.dependencies.map((dependency) => {
                    const { organization, name, version, configurations } = dependency;
                    return (
                        <tr className="dependency" key={[organization, name, version, configurations].join(':')}>
                            <td><Link to={`/artifacts/${organization}/${name}/${version}`}>{organization}</Link></td>
                            <td><Link to={`/artifacts/${organization}/${name}/${version}`}>{name}</Link></td>
                            <td><Link to={`/artifacts/${organization}/${name}/${version}`}>{version}</Link></td>
                            <td><span className="configuration text-center">{configurations.join(', ')}</span></td>
                        </tr>
                    )
                })}
            </tbody>
        </table>
    );
};

const Toolbar = ({ artifact }) => {
    return (
        <div className="cgr-section">
            <div className="cgr-artifacts-toolbar">
                <div>
                    <Link className="btn btn-outline-primary" to={`/viewer/${artifact.organization}/${artifact.name}/${artifact.version}/dependency-graph`}>
                        <i className="fas fa-sitemap fa-1x" /><span className="ml-3">Dependency Graph</span>
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default class ArtifactDetails extends React.Component {

    render() {
        const { artifact } = this.props;
        if (!artifact) {
            return null;
        }

        return (
            <Content header={`${artifact.version} details`}>
                <Section>
                    <Toolbar artifact={artifact} />
                </Section>

                <Section header="Declared dependencies">
                    <DependenciesTable artifact={artifact} />
                </Section>

                <Section header="Info & Warnings">
                    <i>Nothing to show</i>
                </Section>
            </Content>
        );
    }
}
