package com.dnfeitosa.codegraph.services;

import com.dnfeitosa.codegraph.core.model.ImpactZone;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.converters.ApplicationConverter;
import com.dnfeitosa.codegraph.db.graph.converters.ImpactConverter;
import com.dnfeitosa.codegraph.db.graph.converters.ModuleConverter;
import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleService {

	private final ModuleRepository moduleRepository;
    private final ModuleConverter moduleConverter;
    private final ImpactConverter impactZoneConverter;
    private ApplicationConverter applicationConverter;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, ModuleConverter moduleConverter, ImpactConverter impactConverter,
            ApplicationConverter applicationConverter) {
        this.moduleRepository = moduleRepository;
        this.moduleConverter = moduleConverter;
        this.impactZoneConverter = impactConverter;
        this.applicationConverter = applicationConverter;
    }

	public ImpactZone getImpactZone(Module module) {
		List<ImpactResult> fullImpact = moduleRepository.fullImpactOf(module.getName());
		return new ImpactZone(module, impactZoneConverter.fromNodes(fullImpact));
	}

	public Module find(String name) {
        com.dnfeitosa.codegraph.db.graph.nodes.Module moduleNode = moduleRepository.findByName(name);
        Module module = moduleConverter.fromNode(moduleNode);
        module.setApplication(applicationConverter.fromNode(moduleNode.getApplication()));
        return module;
	}
}
