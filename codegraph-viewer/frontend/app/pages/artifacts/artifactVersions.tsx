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

import { Link } from 'react-router-dom';
import Section from "../../components/page/section";

export default class ArtifactVersions extends React.Component<any, any> {

    constructor(props: any) {
        super(props);
        this.state = {  };
    }

    componentDidMount() {
        const { artifact } = this.props;
        apiService.getArtifactVersions(artifact).then((x) => {
            const {versions} = x;
            this.setState({ versions });
        })
    }

    render() {
        const { artifact /*, versions*/ } = this.props;
        const { versions } = this.state;

        if (!versions || !artifact) {
            return null;
        }

        return (
            <Section header="Versions">
                <div>
                {versions.map((version: any) => (
                    <span key={version.version}>
                        <Link to={`/artifacts/${artifact.organization}/${artifact.name}/${version.version}`}>{version.version}</Link>&nbsp;&nbsp;&nbsp;
                    </span>
                ))}
                </div>
            </Section>
        );
    }
}
