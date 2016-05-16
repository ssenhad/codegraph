package com.dnfeitosa.codegraph.db.graph.repositories;

import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ModuleRepository {

    private BaseModuleRepository baseModuleRepository;

    @Autowired
    public ModuleRepository(BaseModuleRepository baseModuleRepository) {
        this.baseModuleRepository = baseModuleRepository;
    }

     public ModuleNode findByName(String module) {
        return baseModuleRepository.findByName(module);
    }

    public Set<ModuleNode> dependenciesOf(String name) {
        return baseModuleRepository.dependenciesOf(name);
    }

    public List<ImpactResult> immediateImpactOf(String moduleName) {
        return baseModuleRepository.immediateImpactOf(moduleName);
    }

    public List<ImpactResult> fullImpactOf(String moduleName) {
        return baseModuleRepository.fullImpactOf(moduleName);
    }

    public ModuleNode save(ModuleNode moduleNode) {
        return baseModuleRepository.save(moduleNode);
    }

    public Set<ModuleNode> dependentsOf(String moduleName) {
        return baseModuleRepository.dependentsOf(moduleName);
    }
}
