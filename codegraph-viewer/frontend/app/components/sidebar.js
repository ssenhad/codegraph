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
