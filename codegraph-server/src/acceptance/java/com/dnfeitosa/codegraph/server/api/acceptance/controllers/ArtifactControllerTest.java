package com.dnfeitosa.codegraph.server.api.acceptance.controllers;

import com.dnfeitosa.codegraph.api.acceptance.controllers.BaseAcceptanceTest;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.nodes.MethodNode;
import com.dnfeitosa.codegraph.db.nodes.ParameterNode;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.db.repositories.TypeRepository;
import com.dnfeitosa.codegraph.db.utils.ResultUtils;
import com.dnfeitosa.codegraph.server.api.controllers.ArtifactController;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactsResource;
import com.dnfeitosa.codegraph.server.api.resources.FieldResource;
import com.dnfeitosa.codegraph.server.api.resources.MethodResource;
import com.dnfeitosa.codegraph.server.api.resources.ParameterResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import com.dnfeitosa.codegraph.server.api.resources.TypesResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertThat;

public class ArtifactControllerTest extends BaseAcceptanceTest {

    @Autowired
    private ArtifactRepository repository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ArtifactController controller;

    private TypeResource arrayList = type("java.util", "ArrayList");
    private TypeResource list = type("java.util", "List");

    private TypeResource type(String packageName, String name) {
        TypeResource type = new TypeResource();
        type.setPackageName(packageName);
        type.setName(name);
        return type;
    }

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
//        artifactResource.addDependency(dependency);

        ResponseEntity<ArtifactResource> response = controller.addArtifact(artifactResource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        ArtifactResource responseResource = response.getBody();

        assertNotNull(responseResource.getId());
        assertThat(responseResource.getName(), is("artifact-name"));
        assertThat(responseResource.getOrganization(), is("artifact-organization"));
        assertThat(responseResource.getVersion(), is("artifact-version"));
        assertThat(responseResource.getExtension(), is("artifact-extension"));
        assertThat(responseResource.getType(), is("artifact-type"));

//        ArtifactResource responseDependency = responseResource.getDependencies().get(0);
//        assertNotNull(responseDependency.getId());
//        assertThat(responseDependency.getName(), is("dependency-name"));
//        assertThat(responseDependency.getVersion(), is("dependency-version"));
//        assertThat(responseDependency.getOrganization(), is("dependency-organization"));
//        assertThat(responseDependency.getExtension(), is("dependency-extension"));
//        assertThat(responseDependency.getType(), is("dependency-type"));
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
//        artifactResource.addDependency(dependency);

        ResponseEntity<ArtifactResource> response = controller.addArtifact(artifactResource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        ArtifactResource responseResource = response.getBody();

        assertNotNull(responseResource.getId());

//        ArtifactResource responseDependency = responseResource.getDependencies().get(0);
//        assertThat(responseDependency.getId(), is(existingDependency.getId()));
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
        assertThat(aartifact.getUri(), is("/api/artifacts/organization/anArtifact/version"));

        ArtifactResource anotherArtifact = artifacts.get(1);
        assertThat(anotherArtifact.getName(), is("anotherArtifact"));
        assertThat(anotherArtifact.getOrganization(), is("organization"));
        assertThat(anotherArtifact.getVersion(), is("version"));
        assertThat(anotherArtifact.getUri(), is("/api/artifacts/organization/anotherArtifact/version"));
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

        MethodResource methodResource = new MethodResource("writingAnArtifactWithItsTypes");
        methodResource.setReturnTypes(asList(list));

        ParameterResource parameter1 = new ParameterResource(list, 1);

        methodResource.setParameters(asList(parameter1));
        type.setMethods(asList(methodResource));
        artifactResource.addType(type);

        FieldResource fieldResource = new FieldResource("repository", type("ArtifactRepository", "com.dnfeitosa.codegraph.db.repositories"));
        type.setFields(asList(fieldResource));

        ResponseEntity<ArtifactResource> response = controller.addArtifact(artifactResource);
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//
//        ArtifactResource responseResource = response.getBody();
//        TypesResource types = responseResource.getTypes();
//        assertThat(types.getUri(), is("/api/artifacts/" + responseResource.getId() + "/types"));
//
//        List<TypeNode> typeNodes = ResultUtils.toList(typeRepository.findAll()).stream().sorted(Comparator.comparing(TypeNode::getName)).collect(toList());
//        assertThat(typeNodes.size(), is(2));
//
//        TypeNode typeNode = typeNodes.get(1);
//        Set<MethodNode> methods = typeNode.getMethods();
//        assertThat(methods.size(), is(1));
//
//        MethodNode method = $(methods).first();
//        System.out.println(method.getId());
////        assertThat(method.getName(), is("writingAnArtifactWithItsTypes"));
//        assertThat(method.getParameters().size(), is(1));
//        ParameterNode parameter = $(method.getParameters()).first();
//        assertThat(parameter.getType().getName(), is("List"));
    }
}
