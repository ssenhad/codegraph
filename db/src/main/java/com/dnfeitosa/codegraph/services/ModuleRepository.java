package com.dnfeitosa.codegraph.services;

import java.util.List;

import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.repositories.GraphModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dnfeitosa.codegraph.db.graph.converters.ImpactConverter;
import com.dnfeitosa.codegraph.core.model.ImpactZone;
import com.dnfeitosa.codegraph.core.model.Module;

@Repository
public class ModuleRepository {

	private final GraphModuleRepository graphRepository;
	private final ImpactConverter impactZoneConverter;

	@Autowired
	public ModuleRepository(GraphModuleRepository graphRepository, ImpactConverter impactConverter) {
		this.graphRepository = graphRepository;
		this.impactZoneConverter = impactConverter;
	}

	public ImpactZone getImpactZone(Module module) {
		List<ImpactResult> fullImpact = graphRepository.fullImpactOf(module.getName());
		return new ImpactZone(module, impactZoneConverter.fromNodes(fullImpact));
	}

	public Module find(String name) {
		return null;
	}
}
