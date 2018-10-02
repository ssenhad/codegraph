import React from 'react';

export default class Container extends React.Component {
    render() {
        return (
            <div className="container-fluid cgr-container">
                <div className="row">
                    {this.props.children}
                </div>
            </div>
        );
    }
}
