import React from 'react';

import Colors from '../../app/components/colors';

const Node = ({ description, className }) => (
    <div style={{display: 'inline-block', textAlign: 'center'}}>
        <div>{description}</div>
        <svg width="150px" height="50px">
            <g className="edgePaths">
                <g className={`edgePath ${className}`} style={{opacity: 1}}>
                    <path className="path"
                          d="M5,30L130,30"
                          markerEnd={`url(#${className.replace(' ', '')})`} style={{fill: 'none'}} />
                    <defs>
                        <marker id={className.replace(' ', '')} viewBox="0 0 10 10" refX="9" refY="5" markerUnits="strokeWidth" markerWidth="8" markerHeight="6"
                                orient="auto">
                            <path d="M 0 0 L 10 5 L 0 10 z" style={{strokeWidth: 1, strokeDasharray: '1, 0'}} />
                        </marker>
                    </defs>
                </g>
            </g>
        </svg>
    </div>
);


const Paths = () => (
    <div>
        <h5>Paths</h5>
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
                    <td><Node className={`${name}`} /></td>
                    <td><Node className={`${name} hover`} /></td>
                    <td><Node className={`${name} selected`} /></td>
                </tr>
            ))}
            </tbody>
        </table>
    </div>
);

export default Paths;
