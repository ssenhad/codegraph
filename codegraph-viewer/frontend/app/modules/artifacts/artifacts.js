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
import { withRouter } from 'react-router-dom';

import Container from '../../components/container';
import Sidebar from '../../components/sidebar';
import Contents from '../../components/contents';

import ArtifactsTree from './artifacts-tree';
import ArtifactOverview from './artifact-overview'

class Artifacts extends React.Component {

    constructor(props) {
        super(props);

        this.select = this.select.bind(this);
        this.state = {};
    }

    static getDerivedStateFromProps(props) {
        const { organization, name, version } = props.match.params;
        return {
            artifact: {
                organization,
                name,
                version
            }
        }
    }

    render() {
        const { artifact } = this.state;
        return (
            <Container>
                <Sidebar title="Artifacts">
                    <ArtifactsTree onSelect={this.select} />
                </Sidebar>
                <Contents>
                    <ArtifactOverview artifact={artifact} key={`${artifact.organization}:${artifact.name}:${artifact.version}`}/>
                </Contents>
            </Container>
        );
    }

    select(artifact) {
        if (artifact.type === 'organization') {
            return;
        }

        this.props.history.push(`/artifacts/${artifact.parent}/${artifact.name}`);
    }
}

export default withRouter(Artifacts);
