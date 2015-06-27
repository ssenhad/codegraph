package com.dnfeitosa.codegraph.model;

public class Dependency {

	private final Jar jar;
	private final Module module;

	public Dependency(Module module, Jar jar) {
		this.module = module;
		this.jar = jar;
	}

	public Jar getJar() {
		return jar;
	}

	public Module getDependent() {
		return module;
	}

	@Override
	public String toString() {
		return String.format("Dependency{%s -=> %s}", module.getName(), jar.getName());
	}
}
