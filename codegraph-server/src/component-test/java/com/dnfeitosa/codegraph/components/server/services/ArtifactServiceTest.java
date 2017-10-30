package com.dnfeitosa.codegraph.components.server.services;

import com.dnfeitosa.codegraph.components.server.ComponentTestBase;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.AvailableVersion;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import com.dnfeitosa.codegraph.server.services.ArtifactService;
import org.junit.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.function.Function;

import static com.dnfeitosa.codegraph.core.utils.Arrays.asSet;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ArtifactServiceTest extends ComponentTestBase {

    @Autowired
    private Session session;

    @Autowired
    private ArtifactService artifactService;

    @Test
    public void shouldReturnTheVersionsOfAnArtifactAndDeclaredDependencies() {
        session.save(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0"));
        session.save(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));

        Set<AvailableVersion> versions = artifactService.getVersions("com.dnfeitosa.codegraph", "codegraph-core");

        assertThat(versions.size(), is((3)));
        assertThat(versions, hasItems(
            new AvailableVersion(new Version("1.0")),
            new AvailableVersion(new Version("1.1"))
        ));
    }

    @Test
    public void shouldReturnTheArtifactsBelongingToAnOrganization() {
        session.save(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0"));
        session.save(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.1"));
        session.save(artifact("com.dnfeitosa.codegraph.foo", "codegraph-foo", "1.1"));

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
            ArtifactNode artifact = artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0")
                .addDependency(artifact("com.dnfeitosa.codegraph", "coollections", "1.0"), asSet("compile"))
                .addDependency(artifact("commons-lang", "commons-lang", "2.6"), asSet("compile"))
                .addDependency(artifact("org.springframework", "spring-context", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifact("junit", "junit", "4.12"), asSet("test"))
                .addDependency(artifact("org.hamcrest", "hamcrest-core", "1.+"), asSet("test"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("com.dnfeitosa.codegraph", "coollections", "1.0")
                .addDependency(artifact("commons-lang", "commons-lang", "2.8"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("org.springframework", "spring-context", "4.2.7.RELEASE")
                .addDependency(artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("org.springframework", "spring-aop", "4.2.7.RELEASE")
                .addDependency(artifact("org.springframework", "spring-beans", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifact("aopalliance", "aopalliance", "1.0"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("org.springframework", "spring-beans", "4.2.7.RELEASE")
                .addDependency(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile"));
            session.save(artifact);
        }{
            ArtifactNode artifact = new ArtifactNode("junit", "junit", "4.12")
                .addDependency(artifact("org.hamcrest", "hamcrest-core", "1.3"), asSet("compile"));
            session.save(artifact);
        }

        Set<ArtifactNode> artifactNodes = artifactService.loadDependencyGraph("com.dnfeitosa.codegraph", "codegraph-core", "1.0");
        assertThat(artifactNodes.size(), is(6));

        find("com.dnfeitosa.codegraph:codegraph-core:1.0").in(artifactNodes)
            .check()
            .exists()
            .hasDependency("com.dnfeitosa.codegraph", "coollections", "1.0", asSet("compile"))
            .hasDependency("commons-lang", "commons-lang", "2.6", asSet("compile"))
            .hasDependency("org.springframework", "spring-context", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("org.springframework", "spring-core", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("junit", "junit", "4.12", asSet("test"))
            .hasDependency("org.hamcrest", "hamcrest-core", "1.+", asSet("test"));

        find("com.dnfeitosa.codegraph:coollections:1.0").in(artifactNodes)
            .check()
            .exists()
            .hasDependency("commons-lang", "commons-lang", "2.8", asSet("compile"));

        find("org.springframework:spring-context:4.2.7.RELEASE").in(artifactNodes)
            .check()
            .exists()
            .hasDependency("org.springframework", "spring-aop", "4.2.7.RELEASE", asSet("compile"));

        find("org.springframework:spring-aop:4.2.7.RELEASE").in(artifactNodes)
            .check()
            .exists()
            .hasDependency("org.springframework", "spring-beans", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("aopalliance", "aopalliance", "1.0", asSet("compile"));

        find("org.springframework:spring-beans:4.2.7.RELEASE").in(artifactNodes)
            .check()
            .exists()
            .hasDependency("org.springframework", "spring-core", "4.2.7.RELEASE", asSet("compile"));

        find("junit:junit:4.12").in(artifactNodes)
            .check()
            .exists()
            .hasDependency("org.hamcrest", "hamcrest-core", "1.3", asSet("compile"));
    }

    private NodeFinder<ArtifactNode> find(String id) {
        return new NodeFinder<>(id, ArtifactNode::getId);
    }

    private ArtifactNode artifact(String organization, String name, String version) {
        return new ArtifactNode(organization, name, version);
    }

    public ArtifactNodeCheck check(ArtifactNode node) {
        return new ArtifactNodeCheck(node);
    }

    public static class ArtifactNodeCheck {
        private ArtifactNode node;

        public ArtifactNodeCheck(ArtifactNode node) {
            this.node = node;
        }

        public ArtifactNodeCheck hasDependency(String organization, String name, String version, Set<String> configurations) {
            assertThat(node.getDeclaredDependencies(), hasItem(new DeclaresRelationship(node, new ArtifactNode(organization, name, version), configurations)));
            return this;
        }

        public ArtifactNodeCheck exists() {
            assertNotNull("ArtifactNode is null", node);
            return this;
        }
    }

    public static class NodeFinder<T extends ArtifactNode> {

        private final String id;
        private final Function<T, String> getValue;

        public NodeFinder(String value, Function<T, String> getValue) {
            this.id = value;
            this.getValue = getValue;
        }

        public Found<T> in(Set<T> nodes) {
            return new Found<>(nodes.stream()
                .filter(n -> getValue.apply(n).equals(id))
                .findFirst().orElseThrow(() -> new ValueNotFound(String.format("Value with id '%s' was not found", id))));
        }

        public static class ValueNotFound extends RuntimeException {
            public ValueNotFound(String message) {
                super(message);
            }
        }

        public class Found<T extends ArtifactNode> {

            private T t;

            public Found(T t) {
                this.t = t;
            }

            public T get() {
                return t;
            }

            public ArtifactNodeCheck check() {
                return new ArtifactNodeCheck(get());
            }
        }
    }
}
