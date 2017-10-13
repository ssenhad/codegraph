package com.dnfeitosa.codegraph.db.models.converters;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Dependency;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.models.ArtifactNode;
import com.dnfeitosa.codegraph.db.models.relationships.DeclaresRelationship;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.codegraph.core.utils.Arrays.asSet;
import static com.dnfeitosa.coollections.Coollections.$;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactNodeConverterTest {

    private ArtifactNodeConverter converter;

    @Before
    public void setUp() {
        converter = new ArtifactNodeConverter();
    }

    @Test
    public void convertsAnArtifactToNode() {
        Artifact artifact = new Artifact("com.dnfeitosa.codegraph", "codegraph-core", new Version("1.0"));
        artifact.addDependency(new Dependency(new Artifact("org.apache.commons", "commons-lang3", new Version("3.4")), asSet("compile")));

        ArtifactNode node = converter.toNode(artifact);

        assertThat(node.getOrganization(), is("com.dnfeitosa.codegraph"));
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
        ArtifactNode artifactNode = new ArtifactNode("com.dnfeitosa.codegraph", "codegraph-core", "1.0");
        artifactNode.addDependency(new ArtifactNode("org.apache.commons", "commons-lang3", "3.4"), asSet("compile"));

        Artifact artifact = converter.toModel(artifactNode);

        assertThat(artifact.getOrganization(), is("com.dnfeitosa.codegraph"));
        assertThat(artifact.getName(), is("codegraph-core"));
        assertThat(artifact.getVersion().getNumber(), is("1.0"));

        List<Dependency> dependencies = artifact.getDependencies();
        assertThat(dependencies.size(), is(1));

        Dependency dependency = dependencies.get(0);
        assertThat(dependency.getOrganization(), is("org.apache.commons"));
        assertThat(dependency.getName(), is("commons-lang3"));
        assertThat(dependency.getVersion().getNumber(), is("3.4"));
        assertThat(dependency.getConfigurations(), is(asSet("compile")));
    }
}
