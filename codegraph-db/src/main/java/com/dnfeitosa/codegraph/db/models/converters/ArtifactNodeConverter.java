package com.dnfeitosa.codegraph.db.models.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.DependencyNode;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import org.springframework.stereotype.Component;

@Component
public class ArtifactNodeConverter {

    public ArtifactNode toNode(Artifact artifact) {
        String organization = artifact.getOrganization();
        String name = artifact.getName();
        String number = artifact.getVersion().getNumber();

        ArtifactNode artifactNode = new ArtifactNode(organization, name, number);
        artifact.getDependencies().forEach(dependency -> {
            artifactNode.addDependency(toNode(dependency), dependency.getConfigurations());
        });

        return artifactNode;
    }

    private DependencyNode toNode(Dependency dependency) {
        String organization = dependency.getOrganization();
        String name = dependency.getName();
        String version = dependency.getVersion().getNumber();
        return new DependencyNode(organization, name, version);
    }

    public Artifact toModel(ArtifactNode node) {
        Artifact artifact = new Artifact(node.getOrganization(), node.getName(), new Version(node.getVersion()));
        node.getDeclaredDependencies().forEach(declaredDependency -> {
            Dependency dependency = toModel(declaredDependency);
            artifact.addDependency(dependency);
        });
        return artifact;
    }

    private Dependency toModel(DeclaresRelationship declared) {
        DependencyNode dependency = declared.getDependency();
        String organization = dependency.getOrganization();
        String name = dependency.getName();
        String version = dependency.getVersion();
        return new Dependency(organization, name, new Version(version), declared.getConfigurations());
    }
}
