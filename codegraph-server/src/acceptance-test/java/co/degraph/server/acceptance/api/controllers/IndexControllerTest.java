package co.degraph.server.acceptance.api.controllers;

import co.degraph.server.acceptance.AcceptanceTestBase;
import co.degraph.server.api.controllers.IndexController;
import co.degraph.server.api.resources.ArtifactResource;
import co.degraph.server.api.resources.DependencyResource;
import co.degraph.server.api.resources.IndexResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static co.degraph.coollections.Coollections.asSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IndexControllerTest extends AcceptanceTestBase {

    @Autowired
    private IndexController controller;

    @Test
    public void indexesAndReturnTheIndexedArtifact() {
        List<DependencyResource> dependencies = asList(new DependencyResource("co.degraph", "coollections", "1.0", asSet("compile")));

        ArtifactResource artifact = new ArtifactResource("co.degraph", "codegraph-core", "1.0", dependencies);

        IndexResource resource = new IndexResource(artifact, asSet());

        ResponseEntity<ArtifactResource> response = controller.index(resource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        ArtifactResource created = response.getBody();
        assertThat(created.getOrganization(), is("co.degraph"));
        assertThat(created.getName(), is("codegraph-core"));
        assertThat(created.getVersion(), is("1.0"));
        assertThat(created.getDependencies().size(), is(1));
    }

    @Test
    public void  shouldIndexTheArtifactDependencyArtifacts() {
        ArtifactResource artifactResource = new ArtifactResource("co.degraph", "codegraph-core", "1.0", asList(
            new DependencyResource("commons-lang", "commons-lang", "2.6", asSet("compile")),
            new DependencyResource("co.degraph", "coollections", "1.0", asSet("compile")),
            new DependencyResource("org.springframework", "spring-context", "4.2.7.RELEASE", asSet("compile")),
            new DependencyResource("org.springframework", "spring-core", "4.2.7.RELEASE", asSet("compile")),
            new DependencyResource("junit", "junit", "4.12", asSet("test")),
            new DependencyResource("org.hamcrest", "hamcrest-core", "1.+", asSet("test"))
        ));

        IndexResource indexResource = new IndexResource(artifactResource, asSet(
            new ArtifactResource("co.degraph", "coollections", "1.0", asList(
                new DependencyResource("commons-lang", "commons-lang", "2.8", emptySet())
            )),
            new ArtifactResource("org.springframework", "spring-context", "4.2.7.RELEASE", asList(
                new DependencyResource("org.springframework", "spring-aop", "4.2.7.RELEASE", emptySet())
            )),
            new ArtifactResource("org.springframework", "spring-aop", "4.2.7.RELEASE", asList(
                new DependencyResource("org.springframework", "spring-beans", "4.2.7.RELEASE", emptySet()),
                new DependencyResource("aopalliance", "aopalliance", "1.0", asSet("compile"))
            )),
            new ArtifactResource("org.springframework", "spring-beans", "4.2.7.RELEASE", asList(
                new DependencyResource("org.springframework", "spring-core", "4.2.7.RELEASE", emptySet())
            )),
            new ArtifactResource("junit", "junit", "4.12", asList(
                new DependencyResource("org.hamcrest", "hamcrest-core", "1.3", emptySet())
            ))
        ));

        ResponseEntity<ArtifactResource> response = controller.index(indexResource);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        ArtifactResource created = response.getBody();
        assertThat(created.getOrganization(), is("co.degraph"));
        assertThat(created.getName(), is("codegraph-core"));
        assertThat(created.getVersion(), is("1.0"));
        assertThat(created.getDependencies().size(), is(6));

        session.clear();

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
            .hasDependency("commons-lang", "commons-lang", "2.8", emptySet());

        db.artifact("org.springframework", "spring-context", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.springframework", "spring-aop", "4.2.7.RELEASE", emptySet());

        db.artifact("org.springframework", "spring-aop", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(2)
            .hasDependency("org.springframework", "spring-beans", "4.2.7.RELEASE", emptySet())
            .hasDependency("aopalliance", "aopalliance", "1.0", emptySet());

        db.artifact("org.springframework", "spring-beans", "4.2.7.RELEASE")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.springframework", "spring-core", "4.2.7.RELEASE", emptySet());

        db.artifact("junit", "junit", "4.12")
            .exists()
            .hasDependencies(1)
            .hasDependency("org.hamcrest", "hamcrest-core", "1.3", emptySet());

        db.artifact("org.hamcrest", "hamcrest-core", "1.3")
            .exists()
            .hasNoDependencies();
    }
}
