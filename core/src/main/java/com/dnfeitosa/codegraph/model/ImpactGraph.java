package com.dnfeitosa.codegraph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImpactGraph {

	private Set<Module> nodes = new HashSet<Module>();
	private List<Impact> paths = new ArrayList<Impact>();

	public Set<Module> getNodes() {
		return nodes;
	}

	public void addPath(Impact impact) {
		paths.add(impact);
		nodes.add(impact.getImpacted());
		nodes.add(impact.getImpacting());
	}

	public List<Impact> getPaths() {
		return paths;
	}
}