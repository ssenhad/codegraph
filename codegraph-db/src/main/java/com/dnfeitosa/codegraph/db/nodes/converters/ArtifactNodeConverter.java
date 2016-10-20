package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import org.springframework.stereotype.Component;

@Component
public class ArtifactNodeConverter {

    private final TypeNodeConverter typeConverter;

    public ArtifactNodeConverter() {
        typeConverter = new TypeNodeConverter();
    }

    public ArtifactNode toNode(Artifact artifact) {
        Long id = artifact.getId();
        String name = artifact.getName();
        String organization = artifact.getOrganization();
        String number = artifact.getVersion().getNumber();
        String artifactType = artifact.getType();
        String extension = artifact.getExtension();

        ArtifactNode node = new ArtifactNode(id, name, organization, number, artifactType, extension);
        artifact.getDependencies().forEach(dependency -> node.addDependency(toNode(dependency)));
        artifact.getTypes().forEach(type -> node.addType(typeConverter.toNode(type)));
        return node;
    }

    public Artifact toModel(ArtifactNode node) {
        Artifact artifact = new Artifact(node.getId(), node.getName(), node.getOrganization(), new Version(node.getVersion()), node.getType(), node.getExtension());
        node.getDependencies().forEach(dependency -> artifact.addDependency(toModel(dependency)));
        return artifact;
    }
}
