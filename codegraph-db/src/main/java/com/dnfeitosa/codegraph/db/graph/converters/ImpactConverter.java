package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Impact;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImpactConverter {

	public List<Impact> fromNodes(List<ImpactResult> impactResults) {
		return null;
	}

	public Impact fromNode(ImpactResult impactResult) {
		return new Impact(toModule(impactResult.getImpactor()), toModule(impactResult.getImpacted()));
	}

	private Module toModule(ModuleNode impactor) {
		return null;
	}
}