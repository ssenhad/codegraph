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

/* 3rd party imports */
import 'bootstrap';

import {HashRouter, Switch, Route} from 'react-router-dom';
import React from 'react';
import ReactDOM from 'react-dom';

import './extensions';

import Navbar from './components/navigation/navbar';
import Artifacts from './pages/artifacts/artifacts';
import Viewer from './pages/viewer/viewer';

const _404 = () => {
    return (
        <span>Ooops... 404</span>
    )
};

const Routes = () => (
    <Switch>
        <Route path="/artifacts" exact component={Artifacts} />
        <Route path="/artifacts/:organization/:name" exact component={Artifacts} />
        <Route path="/artifacts/:organization/:name/:version" component={Artifacts} />
        <Route path="/viewer/:organization/:name/:version" component={Viewer} />
        <Route component={_404} />
    </Switch>
);

ReactDOM.render((
    <HashRouter>
        <React.Fragment>
            <Navbar />
            <Routes />
        </React.Fragment>
    </HashRouter>
), document.getElementById("root"));
