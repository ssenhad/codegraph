package com.dnfeitosa.codegraph.model;

import static com.dnfeitosa.coollections.Coollections.$;

import java.util.Collections;
import java.util.List;

import com.dnfeitosa.coollections.Filter;

public class Application {

	private final String name;
	private final String location;
	private final List<Module> modules;

	public Application(String name) {
		this(name, null, Collections.<Module> emptyList());
	}

	public Application(String name, String location, List<Module> modules) {
		this.name = name;
		this.location = location;
		this.modules = assignToMe(modules);
	}

	private List<Module> assignToMe(List<Module> modules) {
		for (Module module : modules) {
			module.assignTo(this);
		}
		return modules;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public List<Module> getModules() {
		return modules;
	}

	public Module getModule(final String name) {
		return $(modules).find(new Filter<Module>() {
			@Override
			public Boolean matches(Module module) {
				return module.getName().equals(name);
			}
		});
	}
}