package com.dnfeitosa.codegraph.gradle.plugin;

import com.dnfeitosa.codegraph.client.CodegraphClient;
import com.dnfeitosa.codegraph.client.resources.Artifact;
import com.dnfeitosa.codegraph.gradle.plugin.converters.ProjectConverter;
import com.dnfeitosa.codegraph.gradle.plugin.converters.ResolvedDependencyConverter;
import com.dnfeitosa.codegraph.gradle.plugin.resolvers.DependenciesResolver;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.util.Set;

public class CodegraphIndexer {

    private static final Logger LOGGER = Logging.getLogger(CodegraphIndexer.class);

    private final ProjectConverter projectConverter;
    private final ResolvedDependencyConverter dependencyConverter;
    private final DependenciesResolver dependencyResolver;
    private final CodegraphClient client;

    public CodegraphIndexer(CodegraphClient client) {
        this(client, new DependenciesResolver(), new ProjectConverter(), new ResolvedDependencyConverter());
    }

    CodegraphIndexer(CodegraphClient client, DependenciesResolver dependencyResolver, ProjectConverter projectConverter, ResolvedDependencyConverter dependencyConverter) {
        this.client = client;
        this.dependencyResolver = dependencyResolver;
        this.projectConverter = projectConverter;
        this.dependencyConverter = dependencyConverter;
    }

    public void index(Project project) {
        Artifact artifact = projectConverter.toArtifact(project);
        Set<ResolvedDependency> resolvedDependencies = resolveDependencies(project);
        for (ResolvedDependency resolvedDependency : resolvedDependencies) {
            Artifact dependency = dependencyConverter.toArtifact(resolvedDependency);
            artifact.addDependency(dependency);
        }
        client.addArtifact(artifact);
    }

    private Set<ResolvedDependency> resolveDependencies(Project project) {
        return dependencyResolver.resolveDependencies(project);
    }
}
