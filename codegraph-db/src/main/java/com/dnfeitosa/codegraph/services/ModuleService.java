package com.dnfeitosa.codegraph.services;

import com.dnfeitosa.codegraph.core.model.ImpactZone;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.converters.ImpactConverter;
import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleService {

	private final ModuleRepository moduleRepository;
	private final ImpactConverter impactZoneConverter;

	@Autowired
	public ModuleService(ModuleRepository moduleRepository, ImpactConverter impactConverter) {
		this.moduleRepository = moduleRepository;
		this.impactZoneConverter = impactConverter;
	}

	public ImpactZone getImpactZone(Module module) {
		List<ImpactResult> fullImpact = moduleRepository.fullImpactOf(module.getName());
		return new ImpactZone(module, impactZoneConverter.fromNodes(fullImpact));
	}

	public Module find(String name) {
		return null;
	}
}
