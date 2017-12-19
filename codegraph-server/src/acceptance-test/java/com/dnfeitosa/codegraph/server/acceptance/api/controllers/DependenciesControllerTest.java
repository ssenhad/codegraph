package com.dnfeitosa.codegraph.server.acceptance.api.controllers;

import com.dnfeitosa.codegraph.server.acceptance.AcceptanceTestBase;
import com.dnfeitosa.codegraph.server.api.controllers.DependenciesController;
import com.dnfeitosa.codegraph.server.api.resources.GraphResource;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.dnfeitosa.coollections.Coollections.asSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class DependenciesControllerTest extends AcceptanceTestBase {

    @Autowired
    private DependenciesController controller;

    @Autowired
    private ArtifactService service;

    @Test
    public void shouldReturnAnArtifactsDependencyGraph() {
        service.save(artifact("com.dnfeitosa.codegraph", "codegraph-server", "1.0")
            .addDependency(artifact("junit", "junit", "4.12"), asSet("test"))
            .addDependency(artifact("org.springframework", "spring-beans", "4.2.7.RELEASE"), asSet("compile"))
            .addDependency(artifact("com.dnfeitosa.codegraph", "codegraph-db", "1.0"), asSet("compile"))
        );

        service.save(artifact("com.dnfeitosa.codegraph", "codegraph-db", "1.0")
            .addDependency(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0"), asSet("compile"))
        );

        service.save(artifact("junit", "junit", "4.12")
            .addDependency(artifact("org.hamcrest", "hamcrest-core", "1.3"), asSet("compile"))
        );

        ResponseEntity<GraphResource> response = controller.getDependencyGraph("com.dnfeitosa.codegraph", "codegraph-server", "1.0");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        GraphResource resource = response.getBody();

        check(resource)
            .hasRoot("com.dnfeitosa.codegraph:codegraph-server:1.0")
            .hasNodes(6)
            .hasNode("com.dnfeitosa.codegraph:codegraph-server:1.0", node -> {
                node.isArtifact("com.dnfeitosa.codegraph", "codegraph-server", "1.0");
            })
            .hasNode("com.dnfeitosa.codegraph:codegraph-db:1.0", node -> {
                node.isArtifact("com.dnfeitosa.codegraph", "codegraph-db", "1.0");
            })
            .hasNode("com.dnfeitosa.codegraph:codegraph-core:1.0", node -> {
                node.isArtifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0");
            })
            .hasNode("org.springframework:spring-beans:4.2.7.RELEASE", node -> {
                node.isArtifact("org.springframework", "spring-beans", "4.2.7.RELEASE");
            })
            .hasNode("junit:junit:4.12", node -> {
                node.isArtifact("junit", "junit", "4.12");
            })
            .hasNode("org.hamcrest:hamcrest-core:1.3", node -> {
                node.isArtifact("org.hamcrest", "hamcrest-core", "1.3");
            })
            .hasEdges(5)
            .hasEdge("com.dnfeitosa.codegraph:codegraph-server:1.0", "com.dnfeitosa.codegraph:codegraph-db:1.0", edge -> {
                edge.hasAttribute("configurations", asSet("compile"));
            })
            .hasEdge("com.dnfeitosa.codegraph:codegraph-server:1.0", "org.springframework:spring-beans:4.2.7.RELEASE", edge -> {
                edge.hasAttribute("configurations", asSet("compile"));
            })
            .hasEdge("com.dnfeitosa.codegraph:codegraph-server:1.0", "junit:junit:4.12", edge -> {
                edge.hasAttribute("configurations", asSet("test"));
            })
            .hasEdge("com.dnfeitosa.codegraph:codegraph-db:1.0", "com.dnfeitosa.codegraph:codegraph-core:1.0", edge -> {
                edge.hasAttribute("configurations", asSet("compile"));
            })
            .hasEdge("junit:junit:4.12", "org.hamcrest:hamcrest-core:1.3", edge -> {
                edge.hasAttribute("configurations", asSet("compile"));
            });
    }

    @Test
    public void shouldReturnA404WhenArtifactIsNotFound() {
        ResponseEntity<GraphResource> response = controller.getDependencyGraph("com.foo.bar", "baz", "1.0");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertNull(response.getBody());
    }
}
