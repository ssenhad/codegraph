package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
import org.springframework.stereotype.Component;

@Component
public class ProjectNodeConverter {

    public ProjectNode toNode(Project project) {
        return new ProjectNode(project.getId(), project.getName(), project.getOrganization(), project.getVersion());
    }

    public Project toModel(ProjectNode node) {
        return new Project(node.getId(), node.getName(), node.getOrganization(), node.getVersion());
    }
}
