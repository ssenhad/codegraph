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
import {RouteComponentProps, withRouter} from 'react-router-dom';

import Container from '../../components/page/container';
import Sidebar from '../../components/page/sidebar';
import Main from '../../components/page/main';

import ArtifactsTree from './artifactsTree';
import {ArtifactIdentity} from "../../models/core";
import {Node} from '@dnfeitosa/react-treee/models';
import ArtifactVersions from "./artifactVersions";
import ArtifactDetails from "./artifactDetails";

interface UrlParameters {
    organization: string;
    name: string;
    version: string;
}

interface Properties extends RouteComponentProps<UrlParameters> {
}

interface State {
    artifact?: ArtifactIdentity
}

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
                <li className="breadcrumb-item active" aria-current="page">{artifact.version}</li>
            </ol>
        </nav>
    );
};

class Artifacts extends React.Component<Properties, State> {

    constructor(props: Properties) {
        super(props);

        this.select = this.select.bind(this);
        this.state = {};
    }

    render() {
        const { artifact } = this.state;

        return (
            <Container>
                <Sidebar title="Artifacts">
                    <ArtifactsTree onSelect={this.select} />
                </Sidebar>
                <Main>
                    {artifact && artifact.organization && artifact.name && (
                        <React.Fragment>
                            <ArtifactTitle artifact={artifact} key={`title-${artifact.organization}:${artifact.name}:${artifact.version}`}/>
                            <ArtifactVersions artifact={artifact} key={`${artifact.organization}:${artifact.name}`}/>
                            <ArtifactDetails artifact={artifact} key={`details-${artifact.organization}:${artifact.name}:${artifact.version}`} />
                        </React.Fragment>
                    )}
                </Main>
            </Container>
        );
    }

    select(artifact: Node) {
        if (artifact.type === 'organization') {
            return;
        }

        this.props.history.push(`/artifacts/${artifact.parent}/${artifact.name}`);
    }

    static getDerivedStateFromProps(props: RouteComponentProps<UrlParameters>) : State {
        const { organization, name, version } = props.match.params;
        return {
            artifact: {organization, name, version}
        }
    }
}

export default withRouter(Artifacts);
