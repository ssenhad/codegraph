import React from 'react';

const ArtifactTitle = ({ artifact }) => {
    if (!artifact) {
        return null;
    }

    return (
        <div className="cgr-text-heading text-primary">
            <i>{artifact.organization}:{artifact.name}</i>
        </div>
    );
};

export default ArtifactTitle;
