package com.dnfeitosa.codegraph.web.resources;

import java.util.List;

public class ImpactGraphResource {

	private List<ModuleResource> nodes;
	private List<ImpactResource> paths;

	public List<ModuleResource> getNodes() {
		return nodes;
	}

	public void setNodes(List<ModuleResource> nodes) {
		this.nodes = nodes;
	}

	public List<ImpactResource> getPaths() {
		return paths;
	}

	public void setPaths(List<ImpactResource> paths) {
		this.paths = paths;
	}
}