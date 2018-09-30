import React from 'react';

class Artifact extends React.Component {

    render() {
        const {artifact} = this.state;
        return (
            <React.Fragment>
                <div className="cgr-artifact-details" ng-hide="noDetails">
                    <h3><i>{artifact.version}</i> details</h3>
                </div>
                <div className="cgr-section">
                    <div ng-show="artifact.dependencies.length == 0">
                        <i>No dependencies</i>
                    </div>
                    <div ng-show="artifact.dependencies.length > 0">
                        <h4 className=""><b>Declared dependencies</b></h4>
                        <div className="cgr-section">
                            <div className="cgr-artifacts-toolbar">
                                <div>
                                    <a className="btn btn-outline-primary"
                                       href="#!/viewer/{artifact.organization}/{artifact.name}/{artifact.version}/dependency-graph">
                                        <i className="fas fa-sitemap fa-1x"></i><span className="ml-3">Dependency Graph</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <table className="table table-condensed table-responsive-lg">
                            <thead>
                            <tr>
                                <th>Organization</th>
                                <th>Name</th>
                                <th>Version</th>
                                <th>Configurations</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr className="dependency" ng-repeat="dependency in artifact.dependencies | orderBy: ['configurations', 'organization', 'name']">
                                <td><a
                                    href="#!/artifacts/{{dependency.organization}}/{{dependency.name}}/{{dependency.version}}">{ dependency.organization}</a>
                                </td>
                                <td><a
                                    href="#!/artifacts/{{dependency.organization}}/{{dependency.name}}/{{dependency.version}}">{ dependency.name}</a>
                                </td>
                                <td><a
                                    href="#!/artifacts/{{dependency.organization}}/{{dependency.name}}/{{dependency.version}}">{ dependency.version}</a>
                                </td>
                                <td>
                                    <span className="configuration text-center" ng-repeat="configuration in dependency.configurations">{ configuration }</span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
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
            </React.Fragment>
        );
    }
}

