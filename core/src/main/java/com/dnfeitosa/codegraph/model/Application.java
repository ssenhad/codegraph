package com.dnfeitosa.codegraph.model;

import java.util.Collections;
import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

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
		return $(modules).find(module -> module.getName().equals(name));
	}

    private List<Module> assignToMe(List<Module> modules) {
        for (Module module : modules) {
            module.setApplication(this);
        }
        return modules;
    }
}