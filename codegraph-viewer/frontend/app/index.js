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

import 'jstree/dist/themes/default/style.css';

import {HashRouter, Switch, Route} from 'react-router-dom';
import React from 'react';
import ReactDOM from 'react-dom';

import Navbar from './components/navigation/navbar';
import Artifacts from './modules/artifacts/artifacts';

const _404 = () => {
    return (
        <span>Ooops... 404</span>
    )
};

const Main = () => (
    <Switch>
        <Route path="/viewer" component={Viewer} />
        <Route path="/artifacts" exact component={Artifacts} />
        <Route path="/artifacts/:organization/:name" exact component={Artifacts} />
        <Route path="/artifacts/:organization/:name/:version" component={Artifacts} />
        <Route component={_404} />
    </Switch>
);

const App = () => (
    <React.Fragment>
        <Navbar />
        <Main />
    </React.Fragment>
);

class Viewer extends React.Component {
    render() {
        return <div>Viewer</div>
    }
}

ReactDOM.render((
    <HashRouter>
        <App />
    </HashRouter>
), document.getElementById("root"));

