package com.dnfeitosa.codegraph.web.resources;

import java.util.List;

public class DependenciesResource {

	private ModuleResource module;
	private List<DependencyResource> direct;
	private List<DependencyResource> transitive;

	public ModuleResource getModule() {
		return module;
	}

	public void setModule(ModuleResource module) {
		this.module = module;
	}

	public List<DependencyResource> getDirect() {
		return direct;
	}

	public void setDirect(List<DependencyResource> direct) {
		this.direct = direct;
	}

	public List<DependencyResource> getTransitive() {
		return transitive;
	}

	public void setTransitive(List<DependencyResource> transitive) {
		this.transitive = transitive;
	}
}