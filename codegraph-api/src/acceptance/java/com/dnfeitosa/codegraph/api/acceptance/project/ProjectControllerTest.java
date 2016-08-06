package com.dnfeitosa.codegraph.api.acceptance.project;

import com.dnfeitosa.codegraph.api.controllers.ProjectController;
import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import com.dnfeitosa.codegraph.api.resources.ProjectResources;
import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
import com.dnfeitosa.codegraph.db.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/codegraph-db.xml", "classpath:/codegraph-acceptance-test.xml"})
@ActiveProfiles("acceptance")
@Transactional
public class ProjectControllerTest {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private ProjectController controller;

    @Test
    public void acceptingAndWritingAProjectResource() {
        ProjectResource projectResource = new ProjectResource();
        projectResource.setName("project-name");
        projectResource.setOrganization("project-organization");
        projectResource.setVersion("project-version");

        ResponseEntity<ProjectResource> response = controller.addProject(projectResource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        ProjectResource responseResource = response.getBody();

        assertNotNull(responseResource.getId());
        assertThat(responseResource.getName(), is("project-name"));
        assertThat(responseResource.getOrganization(), is("project-organization"));
        assertThat(responseResource.getVersion(), is("project-version"));
    }

    @Test
    public void loadingAProjectResourceByItsId() {
        ProjectNode node = new ProjectNode(null, "project-name", "project-organization", "project-version");
        repository.save(node);

        ResponseEntity<ProjectResource> response = controller.getProject(node.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        ProjectResource resource = response.getBody();
        assertThat(resource.getId(), is(node.getId()));
        assertThat(resource.getName(), is("project-name"));
        assertThat(resource.getOrganization(), is("project-organization"));
        assertThat(resource.getVersion(), is("project-version"));
        assertThat(resource.getUri(), is("/api/projects/" + resource.getId()));
    }

    @Test
    public void returningANotFoundStatusWhenResourceDoesNotExist() {
        ResponseEntity<ProjectResource> response = controller.getProject(999L);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void returningAListOfProjectResources() {
        repository.save(new ProjectNode(null, "aProject", "organization", "version"));
        repository.save(new ProjectNode(null, "anotherProject", "organization", "version"));

        ResponseEntity<ProjectResources> response = controller.getProjects();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ProjectResources resources = response.getBody();
        List<ProjectResource> projects = resources.getProjects();

        assertThat(projects.size(), is(2));
        ProjectResource aProject = projects.get(0);
        assertThat(aProject.getName(), is("aProject"));
        assertThat(aProject.getOrganization(), is("organization"));
        assertThat(aProject.getVersion(), is("version"));
        assertThat(aProject.getUri(), is("/api/projects/" + aProject.getId()));

        ProjectResource anotherProject = projects.get(1);
        assertThat(anotherProject.getName(), is("anotherProject"));
        assertThat(anotherProject.getOrganization(), is("organization"));
        assertThat(anotherProject.getVersion(), is("version"));
        assertThat(anotherProject.getUri(), is("/api/projects/" + anotherProject.getId()));
    }
}
