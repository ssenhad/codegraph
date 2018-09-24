import React from 'react';

import Sidebar from '../../components/sidebar';
import Page from '../../components/page';
import Contents from '../../components/contents';

import ArtifactsTree from './artifacts-tree';

export default class Artifacts extends React.Component {

    render() {
        return (
            <Page>
                <Sidebar title="Artifacts">
                    <ArtifactsTree  />
                </Sidebar>
                <Contents>
                </Contents>
            </Page>
        );
    }
}
