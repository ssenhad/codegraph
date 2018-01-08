package com.dnfeitosa.codegraph.server.acceptance.ui.controllers;

import com.dnfeitosa.codegraph.server.acceptance.AcceptanceTestBase;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import com.dnfeitosa.codegraph.server.ui.controllers.TreeController;
import com.dnfeitosa.codegraph.server.ui.resources.TreeNode;
import com.dnfeitosa.codegraph.server.ui.resources.TreeNodes;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;

public class TreeControllerTest extends AcceptanceTestBase {

    @Autowired
    private TreeController controller;

    @Autowired
    private ArtifactService service;

    @Before
    public void setUp() {
        super.setUp();

        service.save(artifact("co.foo.bar", "bar-baz", "1.0"));
        service.save(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0"));
        service.save(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));
        service.save(artifact("com.dnfeitosa.codegraph", "codegraph-db", "1.0"));
        service.save(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"));
        service.save(artifact("org.springframework.boot", "spring-boot-starter", "1.3.6.RELEASE"));
    }

    @Test
    public void returnsTheRootOrganizations() {
        ResponseEntity<TreeNodes> response = controller.getTreeNodes("");

        assertThat(response.getStatusCode(), is(OK));

        TreeNodes treeNodes = response.getBody();
        assertThat(treeNodes.getNodes().size(), is(3));
        assertThat(treeNodes.getNodes(), hasItems(
            new TreeNode(null, "co", "organization"),
            new TreeNode(null, "com", "organization"),
            new TreeNode(null, "org", "organization")
        ));
    }

    @Test
    public void shouldReturnTheChildOrganizationsAndArtifactsOfAnOrganization() {
        ResponseEntity<TreeNodes> response = controller.getTreeNodes("org.springframework");

        assertThat(response.getStatusCode(), is(OK));

        TreeNodes treeNodes = response.getBody();
        assertThat(treeNodes.getNodes().size(), is(2));
        assertThat(treeNodes.getNodes(), hasItems(
            new TreeNode("org.springframework", "spring-core", "artifact"),
            new TreeNode("org.springframework", "boot", "organization")
        ));
    }

    @Test
    public void returnsTheChildOrganizationsWithParentsNameExactMatch() {
        ResponseEntity<TreeNodes> response = controller.getTreeNodes("co");

        assertThat(response.getStatusCode(), is(OK));

        TreeNodes treeNodes = response.getBody();
        assertThat(treeNodes.getNodes().size(), is(1));
        assertThat(treeNodes.getNodes(), hasItems(
            new TreeNode("co", "foo", "organization")
        ));
    }
}
