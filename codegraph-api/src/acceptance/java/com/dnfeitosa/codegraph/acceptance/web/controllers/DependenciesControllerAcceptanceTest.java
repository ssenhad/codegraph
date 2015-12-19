package com.dnfeitosa.codegraph.acceptance.web.controllers;

import com.dnfeitosa.codegraph.acceptance.support.DatabaseDependentAcceptanceTest;
import com.dnfeitosa.codegraph.web.controllers.DependenciesController;
import com.dnfeitosa.codegraph.web.resources.GraphResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.dnfeitosa.codegraph.db.graph.nodes.builders.Builders.application;
import static com.dnfeitosa.codegraph.db.graph.nodes.builders.Builders.applications;
import static com.dnfeitosa.codegraph.db.graph.nodes.builders.Builders.module;
import static com.dnfeitosa.codegraph.db.graph.nodes.builders.Builders.modules;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DependenciesControllerAcceptanceTest extends DatabaseDependentAcceptanceTest {

    @Autowired
    private DependenciesController controller;

    @Test
    public void shouldReturnAModuleWithItsDependencies() {
        module("A1").exportedBy(application("a")).dependsOn(modules("A2", "C1"));
        module("A2").exportedBy(application("a")).dependsOn(modules("A3", "B1", "D1"));
        module("A3").exportedBy(application("a"));

        module("B1").exportedBy(application("b")).dependsOn(modules("C1", "junit", "log4j"));

        module("C1").exportedBy(application("c")).dependsOn(module("D1"));

        module("D1").exportedBy(application("d"));

        applications("a", "b", "c", "d").each(app -> applicationRepository.save(app.node()));

        ResponseEntity<GraphResource<ModuleResource>> response = controller.dependencies("a", "A1");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        GraphResource<ModuleResource> graphResource = response.getBody();
        assertThat(graphResource.getUri(), is("/applications/a/modules/A1/dependency-graph"));

        ModuleResource root = graphResource.getRoot();
        assertThat(root.getName(), is("A1"));
        assertThat(root.getUri(), is("/applications/a/modules/A1"));

        assertThat(graphResource.getEdges().size(), is(7));
    }
}