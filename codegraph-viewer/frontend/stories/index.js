import React from 'react';

import { storiesOf } from '@storybook/react';

import '../app/scss/index.scss';
import Artifacts from '../stories/viewer/artifacts'
import Paths from '../stories/viewer/paths'

const ContentDecorator = (story) => (
    <div style={{ padding: '30px' }}>
        <div style={{}}>
            {story()}
        </div>
    </div>
);

storiesOf('Viewer Objects', module)
    .addDecorator(ContentDecorator)
    .add('Artifacts', () =>  <Artifacts />)
    .add('Paths', () =>  <Paths />);
