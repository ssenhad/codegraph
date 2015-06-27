package com.dnfeitosa.codegraph.model;

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
		return new Filter<Impact>() {
			public Boolean matches(Impact impact) {
				return !impact.getImpacting().equals(module);
			}
		};
	}

	private Filter<Impact> impactsDirectly() {
		return new Filter<Impact>() {
			public Boolean matches(Impact impact) {
				return impact.getImpacting().equals(module);
			}
		};
	}

	private Filter<Impact> sameApplication() {
		return new Filter<Impact>() {
			public Boolean matches(Impact impact) {
				return impact.getImpacted().getApplication().equals(module.getApplication());
			}
		};
	}
}
