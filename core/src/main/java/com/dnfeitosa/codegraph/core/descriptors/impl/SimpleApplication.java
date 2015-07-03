package com.dnfeitosa.codegraph.core.descriptors.impl;

import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;

import java.util.List;

public class SimpleApplication implements ApplicationDescriptor {

    private final String name;
    private final String location;
    private final List<ModuleDescriptor> modules;

    public SimpleApplication(String name, String location, List<ModuleDescriptor> modules) {
        this.name = name;
        this.location = location;
        this.modules = modules;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public List<ModuleDescriptor> getModules() {
        return modules;
    }
}
