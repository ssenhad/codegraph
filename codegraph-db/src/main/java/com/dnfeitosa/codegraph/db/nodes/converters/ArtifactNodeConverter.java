package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import org.springframework.stereotype.Component;

@Component
public class ArtifactNodeConverter {

    public ArtifactNode toNode(Artifact artifact) {
        return new ArtifactNode(artifact.getId(), artifact.getName(), artifact.getOrganization(), artifact.getVersion().getNumber(), artifact.getType(), artifact.getExtension());
    }

    public Artifact toModel(ArtifactNode node) {
        return new Artifact(node.getId(), node.getName(), node.getOrganization(), new Version(node.getVersion()), node.getType(), node.getExtension());
    }
}
