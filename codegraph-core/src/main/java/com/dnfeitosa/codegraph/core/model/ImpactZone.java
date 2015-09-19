package com.dnfeitosa.codegraph.core.model;

import com.dnfeitosa.coollections.Filter;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class ImpactZone {

	private Module module;
	private List<Impact> impacts;

	public ImpactZone(Module module, List<Impact> impacts) {
		this.module = module;
		this.impacts = impacts;
	}

	public List<Impact> getDirect() {
		return $(impacts).filter(impactsDirectly());
	}

	public List<Impact> getFromSameApplication() {
		return $(impacts).filter(sameApplication());
	}

	public List<Impact> getIndirect() {
		return $(impacts).filter(impactsIndirectly());
	}

	public List<Impact> getFull() {
		return impacts;
	}

	public Module getModule() {
		return module;
	}

	public ImpactGraph toGraph() {
		ImpactGraph graph = new ImpactGraph();
		for (Impact impact : getFull()) {
			graph.addPath(impact);
		}
		return graph;
	}

	private Filter<Impact> impactsIndirectly() {
		return impact -> !impact.getImpacting().equals(module);
	}

	private Filter<Impact> impactsDirectly() {
		return impact -> impact.getImpacting().equals(module);
	}

	private Filter<Impact> sameApplication() {
		return impact -> impact.getImpacted().getApplication().equals(module.getApplication());
	}
}
