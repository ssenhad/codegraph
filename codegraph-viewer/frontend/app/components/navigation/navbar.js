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
