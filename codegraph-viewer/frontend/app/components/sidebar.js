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

export default class Sidebar extends React.Component {

    render() {
        return (
            <div className="col-sm-12 col-md-5 col-lg-3 col-xl-3 d-md-block cgr-sidebar-fixed">
                <div className="cgr-sidebar">
                    <div className="cgr-sidebar-title">{this.props.title}</div>
                    <div className="cgr-sidebar-contents">
                        {this.props.children}
                    </div>
                </div>
            </div>
        );
    }
}
