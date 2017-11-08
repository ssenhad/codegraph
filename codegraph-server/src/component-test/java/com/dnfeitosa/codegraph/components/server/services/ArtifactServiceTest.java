package com.dnfeitosa.codegraph.components.server.services;

import com.dnfeitosa.codegraph.components.server.ComponentTestBase;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Graph;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import com.dnfeitosa.codegraph.server.services.DependencyEdge;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.asSet;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactServiceTest extends ComponentTestBase {

    @Autowired
    private Session session;

    @Autowired
    private ArtifactService artifactService;

    @Test
    public void shouldReturnTheVersionsOfAnArtifactAndDeclaredDependencies() {
        session.save(artifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.0"));
        session.save(artifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));

        Set<AvailableVersion> versions = artifactService.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is((2)));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.0")),
            new AvailableVersion(new Version("1.1"))
        ));
    }

    @Test
    public void shouldReturnTheArtifactsBelongingToAnOrganization() {
        session.save(artifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.0"));
        session.save(artifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));
        session.save(artifactNode("com.dnfeitosa.codegraph.foo", "codegraph-foo", "1.1"));

        Set<Artifact> artifacts = artifactService.getArtifactsFromOrganization("com.dnfeitosa.codegraph");

        assertThat(artifacts.size(), is(2));
        assertThat(artifacts, hasItems(
            new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.0")),
            new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.1"))
        ));
    }

    @Test
    public void shouldLoadTheArtifactDependencyGraph() {
        {
            ArtifactNode artifact = artifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.0")
                .addDependency(artifactNode("com.dnfeitosa.codegraph", "coollections", "1.0"), asSet("compile"))
                .addDependency(artifactNode("commons-lang", "commons-lang", "2.6"), asSet("compile"))
                .addDependency(artifactNode("org.springframework", "spring-context", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifactNode("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifactNode("junit", "junit", "4.12"), asSet("test"))
                .addDependency(artifactNode("org.hamcrest", "hamcrest-core", "1.+"), asSet("test"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("com.dnfeitosa.codegraph", "coollections", "1.0")
                .addDependency(artifactNode("commons-lang", "commons-lang", "2.8"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("org.springframework", "spring-context", "4.2.7.RELEASE")
                .addDependency(artifactNode("org.springframework", "spring-aop", "4.2.7.RELEASE"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("org.springframework", "spring-aop", "4.2.7.RELEASE")
                .addDependency(artifactNode("org.springframework", "spring-beans", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifactNode("aopalliance", "aopalliance", "1.0"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("org.springframework", "spring-beans", "4.2.7.RELEASE")
                .addDependency(artifactNode("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("junit", "junit", "4.12")
                .addDependency(artifactNode("org.hamcrest", "hamcrest-core", "1.3"), asSet("compile"));
            session.save(artifact);
        }

        Graph<Artifact, DependencyEdge> dependencyGraph = artifactService.loadDependencyGraph("com.dnfeitosa.codegraph", "codegraph-core", "1.0");

        check(dependencyGraph)
            .hasRoot("com.dnfeitosa.codegraph:codegraph-core:1.0")
            .hasNodes(12)
                .hasNode("com.dnfeitosa.codegraph:codegraph-core:1.0")
                .hasNode("com.dnfeitosa.codegraph:coollections:1.0")
                .hasNode("commons-lang:commons-lang:2.6")
                .hasNode("commons-lang:commons-lang:2.8")
                .hasNode("org.springframework:spring-context:4.2.7.RELEASE")
                .hasNode("org.springframework:spring-core:4.2.7.RELEASE")
                .hasNode("org.springframework:spring-aop:4.2.7.RELEASE")
                .hasNode("org.springframework:spring-beans:4.2.7.RELEASE")
                .hasNode("aopalliance:aopalliance:1.0")
                .hasNode("junit:junit:4.12")
                .hasNode("org.hamcrest:hamcrest-core:1.3")
                .hasNode("org.hamcrest:hamcrest-core:1.+")
            .hasEdges(12)
                .hasEdge("com.dnfeitosa.codegraph:codegraph-core:1.0", "com.dnfeitosa.codegraph:coollections:1.0", edge -> {
                })
                .hasEdge("com.dnfeitosa.codegraph:codegraph-core:1.0", "commons-lang:commons-lang:2.6", edge -> {
                })
                .hasEdge("com.dnfeitosa.codegraph:codegraph-core:1.0", "org.springframework:spring-context:4.2.7.RELEASE", edge -> {
                })
                .hasEdge("com.dnfeitosa.codegraph:codegraph-core:1.0", "org.springframework:spring-core:4.2.7.RELEASE", edge -> {
                })
                .hasEdge("com.dnfeitosa.codegraph:codegraph-core:1.0", "junit:junit:4.12", edge -> {
                })
                .hasEdge("com.dnfeitosa.codegraph:codegraph-core:1.0", "org.hamcrest:hamcrest-core:1.+", edge -> {
                })
                .hasEdge("com.dnfeitosa.codegraph:coollections:1.0", "commons-lang:commons-lang:2.8", edge -> {
                })
                .hasEdge("org.springframework:spring-context:4.2.7.RELEASE", "org.springframework:spring-aop:4.2.7.RELEASE", edge -> {
                })
                .hasEdge("org.springframework:spring-aop:4.2.7.RELEASE", "org.springframework:spring-beans:4.2.7.RELEASE", edge -> {
                })
                .hasEdge("org.springframework:spring-aop:4.2.7.RELEASE", "aopalliance:aopalliance:1.0", edge -> {
                })
                .hasEdge("org.springframework:spring-beans:4.2.7.RELEASE", "org.springframework:spring-core:4.2.7.RELEASE", edge -> {
                })
                .hasEdge("junit:junit:4.12", "org.hamcrest:hamcrest-core:1.3", edge -> {
                });
    }
}
