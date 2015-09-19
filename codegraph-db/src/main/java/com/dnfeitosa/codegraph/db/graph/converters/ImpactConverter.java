package com.dnfeitosa.codegraph.db.graph.converters;

import java.util.List;

import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import org.springframework.stereotype.Component;

import com.dnfeitosa.codegraph.core.model.Impact;

@Component
public class ImpactConverter {

	public List<Impact> fromNodes(List<ImpactResult> impactResults) {
		return null;
	}

	public Impact fromNode(ImpactResult impactResult) {
		return new Impact(toModule(impactResult.getImpactor()), toModule(impactResult.getImpacted()));
	}

	private com.dnfeitosa.codegraph.core.model.Module toModule(com.dnfeitosa.codegraph.db.graph.nodes.Module impactor) {
		return new com.dnfeitosa.codegraph.core.model.Module(impactor.getName());
	}
}