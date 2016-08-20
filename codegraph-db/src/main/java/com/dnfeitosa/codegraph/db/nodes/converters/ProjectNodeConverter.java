package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
import org.springframework.stereotype.Component;

@Component
public class ProjectNodeConverter {

    public ProjectNode toNode(Project project) {
        ProjectNode projectNode = new ProjectNode(project.getId(), project.getName(), project.getOrganization(), project.getVersion().getNumber());
        project.getArtifacts().forEach(artifact -> projectNode.addArtifact(toNode(artifact)));
        return projectNode;
    }

    private ArtifactNode toNode(Artifact artifact) {
        ArtifactNode node = new ArtifactNode();
        node.setName(artifact.getName());
        node.setExtension(artifact.getExtension());
        node.setType(artifact.getType());
        node.setVersion(artifact.getVersion().getNumber());
        return node;
    }

    public Project toModel(ProjectNode node) {
        Project project = new Project(node.getId(), node.getName(), node.getOrganization(), new Version(node.getVersion()));
        node.getArtifacts().forEach(n -> project.addArtifact(toModel(n)));
        return project;
    }

    private Artifact toModel(ArtifactNode node) {
        return new Artifact(node.getName(), node.getType(), node.getExtension(), new Version(node.getVersion()));
    }
}
