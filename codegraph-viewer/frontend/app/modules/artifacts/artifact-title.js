import React from 'react';

const ArtifactTitle = ({ artifact }) => {
    if (!artifact) {
        return null;
    }

    return (
        <div className="cgr-title shadow-sm">
            {artifact.organization}:{artifact.name}
        </div>
    );
};

export default ArtifactTitle;
