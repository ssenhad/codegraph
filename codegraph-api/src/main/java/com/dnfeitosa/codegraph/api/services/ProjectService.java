package com.dnfeitosa.codegraph.api.services;

import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
import com.dnfeitosa.codegraph.db.nodes.converters.ProjectNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectNodeConverter nodeConverter;
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectNodeConverter nodeConverter, ProjectRepository projectRepository) {
        this.nodeConverter = nodeConverter;
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) {
        ProjectNode node = nodeConverter.toNode(project);
        ProjectNode saved = projectRepository.save(node);
        return nodeConverter.toModel(saved);
    }
}
