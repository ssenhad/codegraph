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

import apiService from '../../services/api-service';

import ArtifactVersions from './artifactVersions';
import ArtifactDetails from './artifactDetails';
import {ArtifactIdentity} from "../../models/core";

const ArtifactTitle = (props: { artifact: ArtifactIdentity }) => {
    const {artifact} = props;
    if (!props.artifact || !props.artifact.organization) {
        return null;
    }

    return (
        <nav aria-label="breadcrumb">
            <ol className="breadcrumb">
                <li className="breadcrumb-item">{artifact.organization}</li>
                <li className="breadcrumb-item active" aria-current="page">{artifact.name}</li>
            </ol>
        </nav>
    );
};

export default class ArtifactOverview extends React.Component<any, any> {

    constructor(props: any) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        const {artifact} = this.props;

        Promise.all([
            apiService.getArtifactVersions(artifact),
            artifact.version && apiService.getArtifact(artifact).then((data) => data),
        ]).then(([{versions, artifact}, details]) => {
            this.setState({versions, artifact, details});
        });
    }

    render() {
        const {versions, details} = this.state;

        return (
            <React.Fragment>
                <ArtifactTitle artifact={this.props.artifact}/>
                <ArtifactVersions artifact={this.props.artifact} versions={versions}/>
                <ArtifactDetails artifact={details}/>
            </React.Fragment>
        )
    }
}
