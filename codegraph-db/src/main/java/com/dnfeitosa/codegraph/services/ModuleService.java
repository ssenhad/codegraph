package com.dnfeitosa.codegraph.services;

import com.dnfeitosa.codegraph.core.model.Dependency;
import com.dnfeitosa.codegraph.core.model.DependencyGraph;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.converters.ApplicationConverter;
import com.dnfeitosa.codegraph.db.graph.converters.ModuleConverter;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import com.dnfeitosa.codegraph.db.graph.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Repository
public class ModuleService {

	private final ModuleRepository moduleRepository;
    private final ModuleConverter moduleConverter;
    private ApplicationConverter applicationConverter;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, ModuleConverter moduleConverter,
                         ApplicationConverter applicationConverter) {
        this.moduleRepository = moduleRepository;
        this.moduleConverter = moduleConverter;
        this.applicationConverter = applicationConverter;
    }

	public Module find(String name) {
        ModuleNode moduleNodeNode = moduleRepository.findByName(name);
        return moduleConverter.fromNode(moduleNodeNode);
    }

    public DependencyGraph loadDependenciesOf(String moduleName) {
        Module root = nodeToModuleWithApplication(moduleRepository.findByName(moduleName));
        Set<ModuleNode> moduleNodes = moduleRepository.dependenciesOf(moduleName);

        Map<Long, Module> nodesById = moduleNodes.stream()
                .collect(toMap(ModuleNode::getId, this::nodeToModuleWithApplication));

        Set<Dependency> relationships = moduleNodes.stream()
                .distinct()
                .flatMap(module -> {
                    return module.getDependencies().stream().map(dep -> {
                        Module dependency = nodesById.get(dep.getId());

                        Jar jar = new Jar(dependency.getOrganization(), dependency.getName(), null);
                        return new Dependency(nodesById.get(module.getId()), jar);
                    });
                })
                .collect(toSet());

        return new DependencyGraph(root, relationships);
    }

    private Module nodeToModuleWithApplication(ModuleNode node) {
        Module module = moduleConverter.fromNode(node);
        if (node.getApplication() != null) {
            module.setApplication(applicationConverter.fromNode(node.getApplication()));
        }
        return module;
    }

    public DependencyGraph loadDependentsOf(String moduleName) {
        Module root = nodeToModuleWithApplication(moduleRepository.findByName(moduleName));
        Set<ModuleNode> moduleNodes = moduleRepository.dependentsOf(moduleName);

        Map<Long, Module> nodesById = moduleNodes.stream()
                .collect(toMap(ModuleNode::getId, this::nodeToModuleWithApplication));

        Set<Dependency> relationships = moduleNodes.stream()
                .distinct()
                .flatMap(module -> {
                    return module.getDependencies().stream().map(dep -> {
                        Module dependency = nodesById.get(dep.getId());
                        if (dependency == null) {
                            return null;
                        }

                        Jar jar = new Jar(dependency.getOrganization(), dependency.getName(), null);
                        return new Dependency(nodesById.get(module.getId()), jar);
                    }).filter(x -> x != null);
                })
                .collect(toSet());

        return new DependencyGraph(root, relationships);
    }
}
