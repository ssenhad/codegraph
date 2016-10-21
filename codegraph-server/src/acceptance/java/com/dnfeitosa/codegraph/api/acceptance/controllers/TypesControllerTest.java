package com.dnfeitosa.codegraph.api.acceptance.controllers;

import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.server.api.controllers.TypesController;
import com.dnfeitosa.codegraph.server.api.resources.TypesResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/codegraph-db.xml", "classpath:/codegraph-acceptance-test.xml"})
@ActiveProfiles("acceptance")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TypesControllerTest {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private TypesController controller;

    @Test
    public void shouldReturnTheTypesBelongingToAnArtifact() {
        ArtifactNode artifactNode = new ArtifactNode(null, "name", "organization", "1.0", "jar", "jar");
        artifactNode.addType(new TypeNode("TypesControllerTest", "com.dnfeitosa.codegraph.api.acceptance.controllers"));
        artifactNode.addType(new TypeNode("ArtifactRepository", "com.dnfeitosa.codegraph.db.repositories"));

        artifactRepository.save(artifactNode);

        ResponseEntity<TypesResource> response = controller.getTypes(artifactNode.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        TypesResource types = response.getBody();
        assertThat(types.getTypes().size(), is(2));
    }
}
