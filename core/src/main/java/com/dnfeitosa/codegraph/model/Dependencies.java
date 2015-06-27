package com.dnfeitosa.codegraph.model;

import com.dnfeitosa.coollections.Filter;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class Dependencies {

	private List<Dependency> dependencies;
	private Module module;

	public Dependencies(List<Dependency> dependencies, Module module) {
		this.dependencies = dependencies;
		this.module = module;
	}

	public Module getModule() {
		return module;
	}

	public List<Dependency> getDirect() {
		return $(dependencies).filter(direct());
	}

	public List<Dependency> getAll() {
		return dependencies;
	}

	public List<Dependency> getTransitive() {
		return $(dependencies).filter(transitive());
	}

	private Filter<Dependency> direct() {
		return new Filter<Dependency>() {
			public Boolean matches(Dependency dependency) {
				return dependency.getDependent().equals(module);
			}
		};
	}

	private Filter<Dependency> transitive() {
		return new Filter<Dependency>() {
			public Boolean matches(Dependency dependency) {
				return !dependency.getDependent().equals(module);
			}
		};
	}
}