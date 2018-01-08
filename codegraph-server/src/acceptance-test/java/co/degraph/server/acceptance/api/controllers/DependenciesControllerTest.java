package co.degraph.server.acceptance.api.controllers;

import co.degraph.server.acceptance.AcceptanceTestBase;
import co.degraph.server.api.controllers.DependenciesController;
import co.degraph.server.api.resources.GraphResource;
import co.degraph.server.services.ArtifactService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static co.degraph.coollections.Coollections.asSet;
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
        service.save(artifact("co.degraph", "codegraph-server", "1.0")
            .addDependency(artifact("junit", "junit", "4.12"), asSet("test"))
            .addDependency(artifact("org.springframework", "spring-beans", "4.2.7.RELEASE"), asSet("compile"))
            .addDependency(artifact("co.degraph", "codegraph-db", "1.0"), asSet("compile"))
        );

        service.save(artifact("co.degraph", "codegraph-db", "1.0")
            .addDependency(artifact("co.degraph", "codegraph-core", "1.0"), asSet("compile"))
        );

        service.save(artifact("junit", "junit", "4.12")
            .addDependency(artifact("org.hamcrest", "hamcrest-core", "1.3"), asSet("compile"))
        );

        ResponseEntity<GraphResource> response = controller.getDependencyGraph("co.degraph", "codegraph-server", "1.0");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        GraphResource resource = response.getBody();

        check(resource)
            .hasRoot("co.degraph:codegraph-server:1.0")
            .hasNodes(6)
            .hasNode("co.degraph:codegraph-server:1.0", node -> {
                node.isArtifact("co.degraph", "codegraph-server", "1.0");
            })
            .hasNode("co.degraph:codegraph-db:1.0", node -> {
                node.isArtifact("co.degraph", "codegraph-db", "1.0");
            })
            .hasNode("co.degraph:codegraph-core:1.0", node -> {
                node.isArtifact("co.degraph", "codegraph-core", "1.0");
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
            .hasEdge("co.degraph:codegraph-server:1.0", "co.degraph:codegraph-db:1.0", edge -> {
                edge.hasAttribute("configurations", asSet("compile"));
            })
            .hasEdge("co.degraph:codegraph-server:1.0", "org.springframework:spring-beans:4.2.7.RELEASE", edge -> {
                edge.hasAttribute("configurations", asSet("compile"));
            })
            .hasEdge("co.degraph:codegraph-server:1.0", "junit:junit:4.12", edge -> {
                edge.hasAttribute("configurations", asSet("test"));
            })
            .hasEdge("co.degraph:codegraph-db:1.0", "co.degraph:codegraph-core:1.0", edge -> {
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
