import React from 'react';

import { Link } from 'react-router-dom';

const DependenciesTable = ({ artifact }) => {
    return (
        <table className="table table-sm table-responsive-lg">
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
            <div className="cgr-artifact-details">
                <h3><i>{artifact.version}</i> details</h3>
                <div className="cgr-section">
                    <div>
                        <Toolbar artifact={artifact} />
                        <h4 className=""><b>Declared dependencies</b></h4>
                        <DependenciesTable artifact={artifact} />
                    </div>
                </div>
                <div className="cgr-section">
                    <div className="panel-heading"><b>Info & Warnings</b></div>
                    <div className="panel-body">
                        <div>
                            <i>Nothing to show</i>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
