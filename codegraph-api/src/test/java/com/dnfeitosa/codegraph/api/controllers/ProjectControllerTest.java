package com.dnfeitosa.codegraph.api.controllers;

import com.dnfeitosa.codegraph.api.converters.ProjectResourceConverter;
import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import com.dnfeitosa.codegraph.api.services.ProjectService;
import com.dnfeitosa.codegraph.core.models.Project;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectControllerTest {

    private long createdId = 10;

    private ProjectService projectService;
    private ProjectResourceConverter projectResourceConverter;

    @Before
    public void setUp() {
        projectService = new ProjectService(null, null) {
            @Override
            public Project addProject(Project project) {
                return new Project(createdId, null, null, null);
            }
        };

        projectResourceConverter = new ProjectResourceConverter() {
            @Override
            public Project toModel(ProjectResource projectResource) {
                return new Project(null, null, null);
            }

            @Override
            public ProjectResource toResource(Project project) {
                return new ProjectResource() {{
                    setId(createdId);
                }};
            }
        };
    }

    @Test
    public void shouldCreateANewProjectAndReturnTheCreatedResource() {
        ProjectController controller = new ProjectController(projectService, projectResourceConverter);

        ResponseEntity<ProjectResource> response = controller.addProject(new ProjectResource());

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getId(), is(createdId));
    }

}