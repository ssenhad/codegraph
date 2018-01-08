package co.degraph.server.component.services;

import co.degraph.server.ComponentTestBase;
import co.degraph.core.models.Artifact;
import co.degraph.server.services.IndexService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static co.degraph.coollections.Coollections.asSet;
import static java.util.Collections.emptySet;

public class IndexServiceTest extends ComponentTestBase {

    @Autowired
    private IndexService indexService;

    @Test
    public void indexAnArtifactAndItsDependencies() {
        Artifact artifact = artifact("co.degraph", "codegraph-server", "1.0")
            .addDependency(artifact("co.degraph", "codegraph-core", "1.0"), asSet("compile"));

        indexService.index(artifact, asSet());

        db.artifact("co.degraph", "codegraph-server", "1.0")
            .exists()
            .hasDependencies(1)
            .hasDependency("co.degraph", "codegraph-core", "1.0", asSet("compile"));

        db.artifact("co.degraph", "codegraph-core", "1.0")
            .exists()
            .hasNoDependencies();
    }

    @Test
    public void indexingAnArtifactWithItsFullDependencyGraph() {
        Artifact artifact = artifact("co.degraph", "codegraph-core", "1.0")
            .addDependency(artifact("org.hamcrest", "hamcrest-core", "1.+"), asSet("test"))
            .addDependency(artifact("junit", "junit", "4.12"), asSet("test"))
            .addDependency(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile"))
            .addDependency(artifact("org.springframework", "spring-context", "4.2.7.RELEASE"), asSet("compile"))
            .addDependency(artifact("co.degraph", "coollections", "1.0"), asSet("compile"))
            .addDependency(artifact("commons-lang", "commons-lang", "2.6"), asSet("compile"));

        Set<Artifact> dependencyArtifacts = asSet(
            artifact("co.degraph", "coollections", "1.0")
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

        db.hasArtifact("co.degraph", "codegraph-core", "1.0");
        db.hasArtifact("co.degraph", "coollections", "1.0");
        db.hasArtifact("org.springframework", "spring-context", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-core", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-aop", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-beans", "4.2.7.RELEASE");
        db.hasArtifact("junit", "junit", "4.12");
        db.hasArtifact("org.hamcrest", "hamcrest-core", "1.+");
        db.hasArtifact("commons-lang", "commons-lang", "2.6");
        db.hasArtifact("commons-lang", "commons-lang", "2.8");

        db.artifact("co.degraph", "codegraph-core", "1.0")
            .exists()
            .hasDependencies(6)
            .hasDependency("commons-lang", "commons-lang", "2.6", asSet("compile"))
            .hasDependency("co.degraph", "coollections", "1.0", asSet("compile"))
            .hasDependency("org.springframework", "spring-context", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("org.springframework", "spring-core", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("junit", "junit", "4.12", asSet("test"))
            .hasDependency("org.hamcrest", "hamcrest-core", "1.+", asSet("test"));

        db.artifact("co.degraph", "coollections", "1.0")
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

    @Test
    public void shouldNotOverrideTheRelationshipDataForExistingArtifactsRepresentingATransitiveDependency() {
        // assumes a previously saved artifact with a dependency declaration containing the configuration
        Artifact springContext = artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
            .addDependency(artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"), asSet("compile"));
        indexService.index(springContext, emptySet());

        // ensures the artifacts created previously won't be used
        artifacts.clear();

        // new artifact is indexed containing a trasitive dependency pointing to an existing artifact
        Artifact artifact = artifact("co.degraph", "codegraph-core", "1.0")
            .addDependency(artifact("org.springframework", "spring-context", "4.2.7.RELEASE"), asSet("compile"));

        Set<Artifact> dependencyArtifacts = asSet(
            artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
                // transitive dependencies are saved without the configuration
                .addDependency(artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"), asSet())
        );

        indexService.index(artifact, dependencyArtifacts);

        session.clear();

        db.hasArtifact("co.degraph", "codegraph-core", "1.0");
        db.hasArtifact("org.springframework", "spring-context", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-aop", "4.2.7.RELEASE");

        db.artifact("co.degraph", "codegraph-core", "1.0")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.springframework", "spring-context", "4.2.7.RELEASE", asSet("compile"));

        db.artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(1)
            // "compile" configuration should not be lost
            .hasDependency("org.springframework", "spring-aop", "4.2.7.RELEASE", asSet("compile"));
    }

    @Test
    public void shouldNotOverrideTheRelationshipDataForDependencyOfAnotherArtifact() {
        {
            // assumes a previously saved artifact with a dependency declaration containing the configuration
            Artifact codegraphDb = artifact("co.degraph", "codegraph-db", "1.0")
                .addDependency(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"), asSet("compile"))
                .addDependency(artifact("org.springframework", "spring-context", "4.2.7.RELEASE"), asSet("compile"));

            Set<Artifact> dependencyArtifacts = asSet(
                artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
                    .addDependency(artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"), emptySet()),

                artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"),

                artifact("org.springframework", "spring-core", "4.2.7.RELEASE")
            );
            indexService.index(codegraphDb, dependencyArtifacts);
        }

        // ensures the artifacts created previously won't be used
        artifacts.clear();

        {
            // new artifact is indexed containing a trasitive dependency pointing to an existing artifact
            Artifact artifact = artifact("co.degraph", "codegraph-server", "1.0")
                .addDependency(artifact("co.degraph", "codegraph-db", "1.0"), asSet("compile"));

            Set<Artifact> dependencyArtifacts = asSet(
                artifact("co.degraph", "codegraph-db", "1.0")
                    .addDependency(artifact("org.springframework", "spring-core", "4.2.7.RELEASE"), emptySet())
                    .addDependency(artifact("org.springframework", "spring-context", "4.2.7.RELEASE"), emptySet()),

                artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
                    .addDependency(artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"), emptySet()),

                artifact("org.springframework", "spring-aop", "4.2.7.RELEASE"),

                artifact("org.springframework", "spring-core", "4.2.7.RELEASE"),

                artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
            );

            indexService.index(artifact, dependencyArtifacts);
        }

        session.clear();

        db.hasArtifact("co.degraph", "codegraph-db", "1.0");
        db.hasArtifact("co.degraph", "codegraph-server", "1.0");
        db.hasArtifact("org.springframework", "spring-core", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-context", "4.2.7.RELEASE");
        db.hasArtifact("org.springframework", "spring-aop", "4.2.7.RELEASE");

        db.artifact("co.degraph", "codegraph-db", "1.0")
            .exists()
            .hasDependencies(2)
            .hasDependency("org.springframework", "spring-core", "4.2.7.RELEASE", asSet("compile"))
            .hasDependency("org.springframework", "spring-context", "4.2.7.RELEASE", asSet("compile"));

        db.artifact("co.degraph", "codegraph-server", "1.0")
            .exists()
            .hasDependencies(1)
            .hasDependency("co.degraph", "codegraph-db", "1.0", asSet("compile"));

        db.artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.springframework", "spring-aop", "4.2.7.RELEASE", emptySet());

        db.artifact("org.springframework", "spring-aop", "4.2.7.RELEASE")
            .exists()
            .hasNoDependencies();

        db.artifact("org.springframework", "spring-core", "4.2.7.RELEASE")
            .exists()
            .hasNoDependencies();

        db.artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.springframework", "spring-aop", "4.2.7.RELEASE", emptySet());
    }
}
