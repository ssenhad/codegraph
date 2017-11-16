package com.dnfeitosa.codegraph.components.server.services;

import com.dnfeitosa.codegraph.components.server.ComponentTestBase;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.server.services.IndexService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.asSet;

public class IndexServiceTest extends ComponentTestBase {

    @Autowired
    private IndexService indexService;

    @Test
    public void whenIndexingAnArtifactAndItsDependencies() {
        Artifact artifact = artifact("com.dnfeitosa.codegraph", "codegraph-server", "1.0")
            .addDependency(artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0"), asSet("compile"));

        indexService.index(artifact, asSet());

        db.artifact("com.dnfeitosa.codegraph", "codegraph-server", "1.0")
            .exists()
            .hasDependencies(1)
            .hasDependency("com.dnfeitosa.codegraph", "codegraph-core", "1.0", asSet("compile"));

        db.artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0")
            .exists()
            .hasNoDependencies();
    }

    @Test
    public void whenIndexingAFullDependencyGraph() {
        Artifact artifact = artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0")
            .addDependency(artifact("org.hamcrest", "hamcrest-core", "1.+"), asSet("test"))
            .addDependency(artifact("junit", "junit", "4.12"), asSet("test"))
            .addDependency(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile"))
            .addDependency(artifact("org.springframework", "spring-context", "4.2.7.RELEASE"), asSet("compile"))
            .addDependency(artifact("com.dnfeitosa.codegraph", "coollections", "1.0"), asSet("compile"))
            .addDependency(artifact("commons-lang", "commons-lang", "2.6"), asSet("compile"));


        Set<Artifact> dependencyArtifacts = asSet(
            artifact("com.dnfeitosa.codegraph", "coollections", "1.0")
                .addDependency(artifact("commons-lang", "commons-lang", "2.8"), asSet("compile")),

            artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
                .addDependency(artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"), asSet("compile")),

            artifact("org.springframework", "spring-aop", "4.2.7.RELEASE")
                .addDependency(artifact("org.springframework", "spring-beans", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifact("aopalliance", "aopalliance", "1.0"), asSet("compile")),

            artifact("org.springframework", "spring-beans", "4.2.7.RELEASE")
                .addDependency(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile")),

            artifact("junit", "junit", "4.12")
                .addDependency(artifact("org.hamcrest", "hamcrest-core", "1.3"), asSet("compile"))
        );

        indexService.index(artifact, dependencyArtifacts);

        db.hasArtifact("com.dnfeitosa.codegraph", "coollections", "1.0");
        db.hasArtifact("org.springframework", "spring-context", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-core", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-aop", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-beans", "4.2.7.RELEASE");
        db.hasArtifact("junit", "junit", "4.12");
        db.hasArtifact("org.hamcrest", "hamcrest-core", "1.+");
        db.hasArtifact("commons-lang", "commons-lang", "2.6");
        db.hasArtifact("commons-lang", "commons-lang", "2.8");

        db.artifact("com.dnfeitosa.codegraph", "codegraph-core", "1.0")
            .exists()
            .hasDependencies(6)
            .hasDependency("commons-lang", "commons-lang", "2.6", asSet("compile"))
            .hasDependency("com.dnfeitosa.codegraph", "coollections", "1.0", asSet("compile"))
            .hasDependency("org.springframework", "spring-context", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("org.springframework", "spring-core", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("junit", "junit", "4.12", asSet("test"))
            .hasDependency("org.hamcrest", "hamcrest-core", "1.+", asSet("test"));

        db.artifact("com.dnfeitosa.codegraph", "coollections", "1.0")
            .exists()
            .hasDependencies(1)
            .hasDependency("commons-lang", "commons-lang", "2.8", asSet("compile"));

        db.artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.springframework", "spring-aop", "4.2.7.RELEASE", asSet("compile"));

        db.artifact("org.springframework", "spring-aop", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(2)
            .hasDependency("org.springframework", "spring-beans", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("aopalliance", "aopalliance", "1.0", asSet("compile"));

        db.artifact("org.springframework", "spring-beans", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.springframework", "spring-core", "4.2.7.RELEASE", asSet("compile"));

        db.artifact("junit", "junit", "4.12")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.hamcrest", "hamcrest-core", "1.3", asSet("compile"));
    }
}