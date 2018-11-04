import React from 'react';

import { storiesOf } from '@storybook/react';

import '../app/scss/index.scss';
import Artifacts from '../stories/viewer/artifacts'

const CenterDecorator = (story) => (
    <div style={{ padding: '15px' }}>
        {story()}
    </div>
);

storiesOf('Viewer Objects', module)
    .addDecorator(CenterDecorator)
    .add('Artifacts', () =>  <Artifacts />);
