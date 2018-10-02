import React from 'react';

const Content = (props) => {
    return (
        <div className="cgr-content">
            {props.header && (
                <div className="cgr-text-heading text-dark">
                    {props.header}
                </div>
            )}
            {props.children}
        </div>
    );
};

const Section = (props) => {
    return (
        <div className="cgr-content-section">
            {props.header && (
                <div className="cgr-text-sub-heading">{props.header}</div>
            )}
            {props.children}
        </div>
    );
};

export { Section };
export default Content;
