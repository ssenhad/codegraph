package com.dnfeitosa.codegraph.acceptance.web.controllers;

import com.dnfeitosa.codegraph.acceptance.support.DatabaseDependentAcceptanceTest;
import com.dnfeitosa.codegraph.web.controllers.DependentsController;
import com.dnfeitosa.codegraph.web.resources.GraphResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DependentsControllerAcceptanceTest extends DatabaseDependentAcceptanceTest {

    @Autowired
    private DependentsController controller;

    @Test
    public void shouldReturnAModuleWithItsDependents() {
        module("A1").exportedBy(application("a")).dependsOn(modules("A2", "C1"));
        module("A2").exportedBy(application("a")).dependsOn(modules("A3", "B1", "D1"));
        module("A3").exportedBy(application("a"));

        module("B1").exportedBy(application("b")).dependsOn(modules("C1", "junit", "log4j"));

        module("C1").exportedBy(application("c")).dependsOn(module("D1"));

        module("D1").exportedBy(application("d"));

        applications("a", "b", "c", "d").each(app -> applicationRepository.save(app.node()));

        ResponseEntity<GraphResource<ModuleResource>> response = controller.dependents("d", "D1");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        GraphResource<ModuleResource> graphResource = response.getBody();
        assertThat(graphResource.getUri(), is("/applications/d/modules/D1/dependents-graph"));

        ModuleResource root = graphResource.getRoot();
        assertThat(root.getName(), is("D1"));
        assertThat(root.getParent().getName(), is("d"));
        assertThat(root.getUri(), is("/applications/d/modules/D1"));

        assertThat(graphResource.getEdges().size(), is(6));
    }
}
