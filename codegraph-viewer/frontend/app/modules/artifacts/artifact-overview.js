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
import React from 'react';

import apiService from '../../services/api-service';

import ArtifactTitle from './artifact-title';
import ArtifactVersions from './artifact-versions';
import ArtifactDetails from './artifact-details';

const ContentsHead = (props) => {
    return (
        <div className={`cgr-contents-head w-100 ${props.className}`}>
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
