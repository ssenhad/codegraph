package com.dnfeitosa.codegraph.api.services;

import com.dnfeitosa.codegraph.core.models.Project;
import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
import com.dnfeitosa.codegraph.db.nodes.converters.ProjectNodeConverter;
import com.dnfeitosa.codegraph.db.repositories.ProjectRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectServiceTest {

    private final long newId = 10;
    private final ProjectNode node = new ProjectNode();

    private ProjectService projectService;
    private ProjectRepository repository;
    private ProjectNodeConverter converter;

    @Before
    public void setUp() {
        converter = mock(ProjectNodeConverter.class);
        when(converter.toNode(any(Project.class))).thenReturn(node);

        repository = mock(ProjectRepository.class);
        ProjectNode newNode = new ProjectNode(null, null, null, null);
        when(repository.save(node)).thenReturn(newNode);

        when(converter.toModel(newNode)).thenReturn(new Project(newId, null, null, null));

        projectService = new ProjectService(converter, repository);
    }

    @Test
    public void shouldConvertAProjectToNodeAndSaveIt() {
        Project savedProject = projectService.addProject(new Project(null, null, null));

        assertThat(savedProject.getId(), is(newId));
    }

}