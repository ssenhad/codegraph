import React from 'react';

export default class Contents extends React.Component {
    render() {
        return (
            <div className="col-sm-12 col-md-7 col-lg-9 col-xl-9 cgr-contents">
                {this.props.children}
            </div>
        );
    }
}
