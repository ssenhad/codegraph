package com.dnfeitosa.codegraph.server.acceptance.api.controllers;

import com.dnfeitosa.codegraph.server.acceptance.AcceptanceTestBase;
import com.dnfeitosa.codegraph.server.api.controllers.IndexController;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.DeclaredDependency;
import com.dnfeitosa.codegraph.server.api.resources.IndexResource;
import com.dnfeitosa.codegraph.server.api.resources.ResolutionResultResource;
import com.dnfeitosa.codegraph.server.api.resources.ResolvedDependency;
import com.dnfeitosa.codegraph.server.api.resources.VersionResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.dnfeitosa.codegraph.core.utils.Arrays.asList;
import static com.dnfeitosa.codegraph.core.utils.Arrays.asSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IndexControllerTest extends AcceptanceTestBase {

    @Autowired
    private IndexController controller;

    @Test
    public void indexesAndReturnTheIndexedArtifact() {
        List<DeclaredDependency> declared = asList(
            new DeclaredDependency("com.dnfeitosa.codegraph", "coollections", "1.0", asSet("compile"))
        );
        List<ResolvedDependency> resolved = asList(
            new ResolvedDependency("com.dnfeitosa.codegraph:codegraph-core:1.0", "com.dnfeitosa.codegraph", "coollections", new VersionResource("1.0", "1.0"))
        );
        ArtifactResource artifact = new ArtifactResource("com.dnfeitosa.codegraph", "codegraph-core", "1.0", declared);

        IndexResource resource = new IndexResource(artifact, new ResolutionResultResource(resolved));

        ResponseEntity<ArtifactResource> response = controller.index(resource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        ArtifactResource created = response.getBody();
        assertThat(created.getOrganization(), is("com.dnfeitosa.codegraph"));
        assertThat(created.getName(), is("codegraph-core"));
        assertThat(created.getVersion(), is("1.0"));
        assertThat(created.getDependencies().size(), is(1));
    }
}