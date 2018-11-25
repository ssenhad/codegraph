import * as React from "react";

export default class DisplayOptions extends React.Component<any, any> {

    constructor() {
        // @ts-ignore
        super();
        this.toggle = this.toggle.bind(this);
        this.state = {expanded: false};
    }

    toggle() {
        this.setState({expanded: !this.state.expanded})
    }

    render() {
        const {expanded} = this.state;
        const icon = expanded ? 'fa-angle-down' : 'fa-angle-up';

        return (
            <div className="mt-auto display-options">
                <div className="head shadow-sm p-1" onClick={this.toggle}>
                    <i className={`px-2 fa ${icon}`}/><span className="h6">Display options</span>
                </div>
                <div className={`body ${ expanded && 'expanded p-2'}`}>
                    <div><b>Node labels</b></div>
                    <div>
                        <div><input type="checkbox" defaultChecked={true}></input> Dependencies</div>
                        {/*<div><input type="checkbox"></input> Types</div>*/}
                        {/*<div><input type="checkbox"></input> Fields</div>*/}
                        {/*<div><input type="checkbox"></input> Methods</div>*/}
                    </div>
                </div>
            </div>
        );
    }
}
