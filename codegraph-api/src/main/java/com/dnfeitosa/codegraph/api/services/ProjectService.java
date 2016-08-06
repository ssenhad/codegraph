package com.dnfeitosa.codegraph.api.services;

import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
import com.dnfeitosa.codegraph.db.nodes.converters.ProjectNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dnfeitosa.codegraph.db.utils.ResultUtils.stream;
import static java.util.stream.Collectors.toList;

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

    public Project loadProject(Long id) {
        ProjectNode node = projectRepository.findOne(id);
        if (node == null) {
            throw new ItemDoesNotExistException(String.format("Project '%d' does not exist.", id));
        }
        return nodeConverter.toModel(node);
    }

    public List<Project> loadAll() {
        Result<ProjectNode> all = projectRepository.findAll();
        return stream(all)
                .map(x -> nodeConverter.toModel(x))
                .collect(toList());
    }
}
