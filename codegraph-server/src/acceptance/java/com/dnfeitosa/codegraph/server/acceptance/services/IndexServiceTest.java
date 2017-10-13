package com.dnfeitosa.codegraph.server.acceptance.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import com.dnfeitosa.codegraph.db.repositories.ArtifactRepository;
import com.dnfeitosa.codegraph.server.acceptance.AcceptanceTestBase;
import com.dnfeitosa.codegraph.server.services.IndexService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static com.dnfeitosa.codegraph.core.utils.Arrays.asSet;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IndexServiceTest extends AcceptanceTestBase {

    @Autowired
    private IndexService indexService;

    @Autowired
    private ArtifactRepository artifactRepository;

    @Test
    public void whenIndexingAnArtifactAndItsDependencies() {
        Artifact artifact = new Artifact("com.dnfeitosa.codegraph", "codegraph-server", new Version("1.0"));
        Set<String> configurations = asSet("compile");
        Dependency dependency = new Dependency(new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.0")), configurations);
        artifact.addDependency(dependency);

        indexService.index(artifact);

        shouldCreateAnArtifactNodeFor(artifact);
        shouldCreateTheArtifactNodeFor(dependency);
        shouldCreateTheRelationshipBetween(artifact, dependency, configurations);
    }

    private void shouldCreateAnArtifactNodeFor(Artifact artifact) {
        ArtifactNode artifactNode = load(artifact);

        assertThat(artifactNode.getName(), is(artifact.getName()));
        assertThat(artifactNode.getOrganization(), is(artifact.getOrganization()));
        assertThat(artifactNode.getVersion(), is(artifact.getVersion().getNumber()));
    }

    private void shouldCreateTheArtifactNodeFor(Dependency dependency) {
        String organization = dependency.getOrganization();
        String name = dependency.getName();
        String version = dependency.getVersion().getNumber();

        ArtifactNode dependencyNode = artifactRepository.load(organization, name, version);
        assertThat(dependencyNode.getName(), is(name));
        assertThat(dependencyNode.getOrganization(), is(organization));
        assertThat(dependencyNode.getVersion(), is(version));
    }

    private void shouldCreateTheRelationshipBetween(Artifact artifact, Dependency dependency, Set<String> configurations) {
        ArtifactNode artifactNode = load(artifact);

        ArtifactNode dependencyNode = new ArtifactNode(dependency.getOrganization(), dependency.getName(), dependency.getVersion().getNumber());
        assertThat(artifactNode.getDeclaredDependencies(), hasItem(new DeclaresRelationship(artifactNode, dependencyNode, configurations)));
    }

    private ArtifactNode load(Artifact artifact) {
        String organization = artifact.getOrganization();
        String name = artifact.getName();
        String version = artifact.getVersion().getNumber();

        return artifactRepository.load(organization, name, version);
    }
}
