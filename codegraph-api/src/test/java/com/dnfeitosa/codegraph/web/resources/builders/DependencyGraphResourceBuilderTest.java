package com.dnfeitosa.codegraph.web.resources.builders;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.Dependency;
import com.dnfeitosa.codegraph.core.model.DependencyGraph;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.web.components.ModuleResourceBuilder;
import com.dnfeitosa.codegraph.web.resources.EdgeResource;
import com.dnfeitosa.codegraph.web.resources.GraphResource;
import com.dnfeitosa.codegraph.web.resources.JarResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import com.dnfeitosa.codegraph.web.resources.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.asSet;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DependencyGraphResourceBuilderTest {

    private final String applicationName = "applicationName";
    private final Module rootModule = module("rootModule", applicationName);

    private DependencyGraphResourceBuilder resourceBuilder;

    @Before
    public void setUp() {
        resourceBuilder = new DependencyGraphResourceBuilder(new ModuleResourceBuilder());
    }

    @Test
    public void shouldConvertADependencyGraphToAGraphResource() {
        Set<Dependency> dependencies = asSet(
                new Dependency(module("A", "a"), jar("B")),
                new Dependency(module("A", "a"), jar("C")),
                new Dependency(module("B", "b"), jar("D")),
                new Dependency(module("D", "d"), jar("E")),
                new Dependency(module("E", null), jar("F"))
        );

        DependencyGraph dependencyGraph = new DependencyGraph(rootModule, dependencies);

        GraphResource<ModuleResource> graphResource = resourceBuilder.build(dependencyGraph, "dependency-graph");

        assertThat(graphResource.getRoot().getName(), is("rootModule"));
        assertThat(graphResource.getRoot().getParent().getName(), is(applicationName));

        assertThat(graphResource.getUri(), is("/applications/applicationName/modules/rootModule/dependency-graph"));

        List<EdgeResource<Resource, Resource>> edges = sortedResources(graphResource.getEdges());
        assertThat(edges.size(), is(5));

        assertIsEdgeRepresentingDependency(edges.get(0), "A", "a", "B");
        assertIsEdgeRepresentingDependency(edges.get(1), "A", "a", "C");
        assertIsEdgeRepresentingDependency(edges.get(2), "B", "b", "D");
        // To be fixed as part of issue #12
        assertIsEdgeRepresentingDependency(edges.get(3), "E", "buggy-relationship", "F");
        assertIsEdgeRepresentingDependency(edges.get(4), "D", "d", "E");
    }

    private List<EdgeResource<Resource, Resource>> sortedResources(Set<EdgeResource<Resource, Resource>> edges) {
        Comparator<EdgeResource> edgeComparator = Comparator
                .<EdgeResource, String>comparing(dep -> dep.getStartNode().getUri())
                .thenComparing(dep -> dep.getEndNode().getUri());

        return edges.stream()
                .sorted(edgeComparator)
                .collect(toList());
    }

    private void assertIsEdgeRepresentingDependency(EdgeResource<Resource, Resource> edge, String dependentName,
                                                    String applicationName, String dependencyName) {
        ModuleResource startNode = (ModuleResource) edge.getStartNode();
        JarResource endNode = (JarResource) edge.getEndNode();
        assertThat(startNode.getName(), is(dependentName));
        assertThat(endNode.getName(), is(dependencyName));
    }

    private Jar jar(String name) {
        return new Jar("organization", name, "version");
    }

    private Module module(String name, String applicationName) {
        Module module = new Module(name, "organization", "version", emptyList(), emptySet());

        if (applicationName != null) {
            module.setApplication(new Application(applicationName));
        }
        return module;
    }

}