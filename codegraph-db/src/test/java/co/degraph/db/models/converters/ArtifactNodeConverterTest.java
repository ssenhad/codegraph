package co.degraph.db.models.converters;

import co.degraph.core.models.Artifact;
import co.degraph.core.models.Artifacts;
import co.degraph.core.models.Dependency;
import co.degraph.core.models.Version;
import co.degraph.db.models.ArtifactNode;
import co.degraph.db.models.relationships.DeclaresRelationship;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static co.degraph.coollections.Coollections.$;
import static co.degraph.coollections.Coollections.asSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactNodeConverterTest {

    private ArtifactNodeConverter converter;
    private Artifacts artifacts;

    @Before
    public void setUp() {
        artifacts = new Artifacts();
        converter = new ArtifactNodeConverter(() -> artifacts);
    }

    @Test
    public void convertsAnArtifactToNode() {
        Artifact artifact = artifacts.artifact("co.degraph", "codegraph-core", new Version("1.0"));
        artifact.addDependency(artifacts.artifact("org.apache.commons", "commons-lang3", new Version("3.4")), asSet("compile"));

        ArtifactNode node = converter.toNode(artifact);

        assertThat(node.getOrganization(), is("co.degraph"));
        assertThat(node.getName(), is("codegraph-core"));
        assertThat(node.getVersion(), is(new Version("1.0").getNumber()));

        Set<DeclaresRelationship> declaredDependencies = node.getDeclaredDependencies();
        assertThat(declaredDependencies.size(), is(1));

        DeclaresRelationship declaredDependency = $(declaredDependencies).first();

        assertThat(declaredDependency.getConfigurations(), is(asSet("compile")));
        ArtifactNode dependencyNode = declaredDependency.getDependency();
        assertThat(dependencyNode.getOrganization(), is("org.apache.commons"));
        assertThat(dependencyNode.getName(), is("commons-lang3"));
        assertThat(dependencyNode.getVersion(), is("3.4"));
    }

    @Test
    public void convertsAnArtifactNodeToModel() {
        ArtifactNode artifactNode = new ArtifactNode("co.degraph", "codegraph-core", "1.0");
        artifactNode.addDependency(new ArtifactNode("org.apache.commons", "commons-lang3", "3.4"), asSet("compile"));

        Artifact artifact = converter.toModel(artifactNode);

        assertThat(artifact.getOrganization(), is("co.degraph"));
        assertThat(artifact.getName(), is("codegraph-core"));
        assertThat(artifact.getVersion().getNumber(), is("1.0"));

        Set<Dependency> dependencies = artifact.getDependencies();
        assertThat(dependencies.size(), is(1));

        Dependency dependency = $(dependencies).first();
        assertThat(dependency.getOrganization(), is("org.apache.commons"));
        assertThat(dependency.getName(), is("commons-lang3"));
        assertThat(dependency.getVersion().getNumber(), is("3.4"));
        assertThat(dependency.getConfigurations(), is(asSet("compile")));
    }
}
