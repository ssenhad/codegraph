package com.dnfeitosa.codegraph.web.resources;

public class DependencyResource {

	private ModuleResource dependent;
	private JarResource dependency;

	public ModuleResource getDependent() {
		return dependent;
	}

	public void setDependent(ModuleResource dependent) {
		this.dependent = dependent;
	}

	public JarResource getDependency() {
		return dependency;
	}

	public void setDependency(JarResource dependency) {
		this.dependency = dependency;
	}
}