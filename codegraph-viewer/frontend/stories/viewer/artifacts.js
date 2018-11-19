import React from 'react';

import Colors from '../../app/components/colors';

const Node = ({ description, className }) => (
    <div style={{display: 'inline-block', textAlign: 'center'}}>
        <div>{description}</div>
        <svg width="150px" height="50px">
            <g className="nodes" transform="translate(75,25)" style={{ opacity: 1 }}>
                <g className={`node ${className}`} transform="translate(0, 0)" style={{ opacity: 1 }}>
                    <rect rx="0" ry="0" x="-69" y="-18.5" width="138" height="35"/>
                    <g className="label" transform="translate(0,0)">
                        <g transform="translate(-44,-9.5)">
                            <text>
                                <tspan space="preserve" dy="1em" x="1">artifact-name</tspan>
                            </text>
                        </g>
                    </g>
                </g>
            </g>
        </svg>
    </div>
);

const Artifacts = () => (
    <div>
        <h5>Artifacts</h5>
        <table>
            <thead style={{textAlign: 'center'}}>
                <tr>
                    <th>Color</th>
                    <th>Default</th>
                    <th>Hover</th>
                    <th>Selected</th>
                </tr>
            </thead>
            <tbody>
            {Colors.ALL.map(({ name, value }) => (
                <tr key={value}>
                    <td>{name}</td>
                    <td><Node className={`bg-${name}`} /></td>
                    <td><Node className={`bg-${name} hover`} /></td>
                    <td><Node className={`bg-${name} selected`} /></td>
                </tr>
            ))}
            </tbody>
        </table>
    </div>
);

export default Artifacts;
