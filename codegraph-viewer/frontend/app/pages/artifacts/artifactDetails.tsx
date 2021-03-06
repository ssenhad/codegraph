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
import * as React from 'react';

import {Link} from 'react-router-dom';

import apiService from '../../services/api-service';

import Section from '../../components/page/section';

const DependenciesTable = (props: { artifact: any }) => {
    const { artifact } = props;
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
                {artifact.dependencies.map((dependency: any) => {
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

const Toolbar = (props: { artifact: any }) => {
    const { artifact } = props;
    return (
        <div className="cgr-artifacts-toolbar">
            <div>
                <Link className="btn btn-outline-primary" to={`/viewer/${artifact.organization}/${artifact.name}/${artifact.version}/dependency-graph`}>
                    <i className="fas fa-sitemap fa-1x" /><span className="ml-3">Dependency Graph</span>
                </Link>
            </div>
        </div>
    );
};

export default class ArtifactDetails extends React.Component<any, any> {

    constructor(props: any) {
        super(props);
        this.state = { }
    }

    componentDidMount() {
        const { artifact } = this.props;
        artifact && artifact.version && apiService.getArtifact(artifact).then((artifact) => {
            this.setState({ artifact });
        });
    }

    render() {
        const { artifact } = this.state;
        if (!artifact) {
            return null;
        }

        return (
            <React.Fragment>
                <Section>
                    <Toolbar artifact={artifact} />
                </Section>

                <Section header="Declared dependencies">
                    <DependenciesTable artifact={artifact} />
                </Section>

                <Section header="Info & Warnings">
                    <i>Nothing to show</i>
                </Section>
            </React.Fragment>
        );
    }
}
