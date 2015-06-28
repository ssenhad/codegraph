package com.dnfeitosa.codegraph.model;

import java.util.Collections;
import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class Application {

	private final String name;
	private final List<Module> modules;

	public Application(String name) {
		this(name, Collections.<Module> emptyList());
	}

	public Application(String name, List<Module> modules) {
		this.name = name;
		this.modules = assignToMe(modules);
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return null;
	}

	public List<Module> getModules() {
		return modules;
	}

	public Module getModule(final String name) {
		return $(modules).find(module -> module.getName().equals(name));
	}

    private List<Module> assignToMe(List<Module> modules) {
        modules.forEach(m -> m.setApplication(this));
        return modules;
    }
}