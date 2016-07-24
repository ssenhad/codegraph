package com.dnfeitosa.codegraph.api.acceptance.project;

import com.dnfeitosa.codegraph.api.main.CodegraphApi;
import com.dnfeitosa.codegraph.api.resources.ProjectResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.dnfeitosa.codegraph.core.utils.PathUtils.join;
import static org.hamcrest.CoreMatchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertThat;

@WebIntegrationTest("server.port=9292")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(value = CodegraphApi.class, locations = { "classpath:/codegraph-db-acceptance-test.xml" })
@ActiveProfiles("acceptance")
public class ProjectApiTest {

    private static final String BASE_URL = "http://localhost:9292";

    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void shouldAcceptAndWriteAProjectResource() {
        ProjectResource projectResource = new ProjectResource();
        projectResource.setName("project-name");
        projectResource.setOrganization("project-organization");
        projectResource.setVersion("project-version");

        ResponseEntity<ProjectResource> response = template.postForEntity(join(BASE_URL, "projects"), projectResource, ProjectResource.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        ProjectResource responseResource = response.getBody();

        assertNotNull(responseResource.getId());
        assertThat(responseResource.getName(), is("project-name"));
        assertThat(responseResource.getOrganization(), is("project-organization"));
        assertThat(responseResource.getVersion(), is("project-version"));
    }
}
