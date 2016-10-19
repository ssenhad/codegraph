package com.dnfeitosa.codegraph.server.api.acceptance.controllers;

import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.db.repositories.TypeRepository;
import com.dnfeitosa.codegraph.db.utils.ResultUtils;
import com.dnfeitosa.codegraph.server.api.controllers.ArtifactController;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import com.dnfeitosa.codegraph.server.api.resources.TypesResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
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
public class ArtifactControllerTest {

    @Autowired
    private ArtifactRepository repository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ArtifactController controller;

    @Test
    public void acceptingAndWritingAartifactResource() {
        ArtifactResource artifactResource = new ArtifactResource();
        artifactResource.setName("artifact-name");
        artifactResource.setOrganization("artifact-organization");
        artifactResource.setVersion("artifact-version");
        artifactResource.setType("artifact-type");
        artifactResource.setExtension("artifact-extension");

        ArtifactResource dependency = new ArtifactResource();
        dependency.setExtension("dependency-extension");
        dependency.setName("dependency-name");
        dependency.setType("dependency-type");
        dependency.setVersion("dependency-version");
        dependency.setOrganization("dependency-organization");
        artifactResource.addDependency(dependency);

        ResponseEntity<ArtifactResource> response = controller.addArtifact(artifactResource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        ArtifactResource responseResource = response.getBody();

        assertNotNull(responseResource.getId());
        assertThat(responseResource.getName(), is("artifact-name"));
        assertThat(responseResource.getOrganization(), is("artifact-organization"));
        assertThat(responseResource.getVersion(), is("artifact-version"));
        assertThat(responseResource.getExtension(), is("artifact-extension"));
        assertThat(responseResource.getType(), is("artifact-type"));

        ArtifactResource responseDependency = responseResource.getDependencies().get(0);
        assertNotNull(responseDependency.getId());
        assertThat(responseDependency.getName(), is("dependency-name"));
        assertThat(responseDependency.getVersion(), is("dependency-version"));
        assertThat(responseDependency.getOrganization(), is("dependency-organization"));
        assertThat(responseDependency.getExtension(), is("dependency-extension"));
        assertThat(responseDependency.getType(), is("dependency-type"));
    }

    @Test
    public void shouldLinkADependencyToAnArtifactIfItAlreadyExists() {
        ArtifactNode existingDependency = new ArtifactNode(null, "dependency-name", "dependency-organization", "dependency-version", "dependency-type", "dependency-extension");
        repository.save(existingDependency);

        ArtifactResource artifactResource = new ArtifactResource();
        artifactResource.setName("artifact-name");
        artifactResource.setOrganization("artifact-organization");
        artifactResource.setVersion("artifact-version");
        artifactResource.setType("artifact-type");
        artifactResource.setExtension("artifact-extension");

        ArtifactResource dependency = new ArtifactResource();
        dependency.setName("dependency-name");
        dependency.setOrganization("dependency-organization");
        dependency.setVersion("dependency-version");
        dependency.setType("dependency-type");
        dependency.setExtension("dependency-extension");
        artifactResource.addDependency(dependency);

        ResponseEntity<ArtifactResource> response = controller.addArtifact(artifactResource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        ArtifactResource responseResource = response.getBody();

        assertNotNull(responseResource.getId());

        ArtifactResource responseDependency = responseResource.getDependencies().get(0);
        assertThat(responseDependency.getId(), is(existingDependency.getId()));
    }

    @Test
    public void loadingAartifactResourceByItsId() {
        ArtifactNode node = new ArtifactNode(null, "artifact-name", "artifact-organization", "artifact-version", "type", "extension");
        repository.save(node);

        ResponseEntity<ArtifactResource> response = controller.getArtifact(node.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        ArtifactResource resource = response.getBody();
        assertThat(resource.getId(), is(node.getId()));
        assertThat(resource.getName(), is("artifact-name"));
        assertThat(resource.getOrganization(), is("artifact-organization"));
        assertThat(resource.getVersion(), is("artifact-version"));
        assertThat(resource.getUri(), is("/api/artifacts/" + resource.getId()));
    }

    @Test
    public void returningANotFoundStatusWhenResourceDoesNotExist() {
        ResponseEntity<ArtifactResource> response = controller.getArtifact(999L);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void returningAListOfartifactResources() {
        repository.save(new ArtifactNode(null, "anArtifact", "organization", "version", "type", "extension"));
        repository.save(new ArtifactNode(null, "anotherArtifact", "organization", "version", "type", "extension"));

        ResponseEntity<ArtifactsResource> response = controller.getArtifacts();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ArtifactsResource resources = response.getBody();
        List<ArtifactResource> artifacts = resources.getArtifacts();

        assertThat(artifacts.size(), is(2));
        ArtifactResource aartifact = artifacts.get(0);
        assertThat(aartifact.getName(), is("anArtifact"));
        assertThat(aartifact.getOrganization(), is("organization"));
        assertThat(aartifact.getVersion(), is("version"));
        assertThat(aartifact.getUri(), is("/api/artifacts/" + aartifact.getId()));

        ArtifactResource anotherArtifact = artifacts.get(1);
        assertThat(anotherArtifact.getName(), is("anotherArtifact"));
        assertThat(anotherArtifact.getOrganization(), is("organization"));
        assertThat(anotherArtifact.getVersion(), is("version"));
        assertThat(anotherArtifact.getUri(), is("/api/artifacts/" + anotherArtifact.getId()));
    }

    @Test
    public void writingAnArtifactWithItsTypes() {
        ArtifactResource artifactResource = new ArtifactResource();
        artifactResource.setName("artifact-name");
        artifactResource.setOrganization("artifact-organization");
        artifactResource.setVersion("artifact-version");
        artifactResource.setType("artifact-type");
        artifactResource.setExtension("artifact-extension");

        TypeResource type = new TypeResource();
        type.setName("TypeResource");
        type.setPackageName("com.dnfeitosa.codegraph.server.api.resources");
        type.setUsage("application");
        type.setType("class");
        artifactResource.addType(type);

        ResponseEntity<ArtifactResource> response = controller.addArtifact(artifactResource);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        ArtifactResource responseResource = response.getBody();
        TypesResource types = responseResource.getTypes();
        assertThat(types.getUri(), is("/api/artifacts/" + responseResource.getId() + "/types"));

        List<TypeNode> typeNodes = ResultUtils.toList(typeRepository.findAll());
        assertThat(typeNodes.size(), is(1));
    }
}
