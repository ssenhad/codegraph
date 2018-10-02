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

export default class Navbar extends React.Component {

    render() {
        return (
            <nav className="navbar navbar-dark navbar-expand-lg navbar-expand fixed-top bg-dark shadow">
                <button type="button" className="navbar-toggler" data-toggle="collapse" data-target="#topBar" aria-expanded="false" aria-controls="topBar">
                    <span className="navbar-toggler-icon" />
                </button>
                <a className="navbar-brand" href="#"><strong>Codegraph</strong></a>

                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav mr-auto px-3">
                        <li className="nav-item text-nowrap">
                            <a className="nav-link" href="#/artifacts">Artifacts</a>
                        </li>
                        <li className="nav-item text-nowrap">
                            <a className="nav-link" href="#/viewer">Viewer</a>
                        </li>
                    </ul>
                    <form className="form-inline mt-2 mt-md-0">
                        <input type="text" name="artifact" className="form-control form-control-sm mr-sm-2" placeholder="Search..." />
                        <button className="btn btn-outline-primary btn-sm my-2 my-sm-0" type="submit"><i className="fas fa-search" /></button>
                    </form>
                </div>
            </nav>
        )
    }
}
