package com.dnfeitosa.codegraph.descriptors;

import java.util.List;

public interface ApplicationDescriptor {
    String getName();
    String getLocation();
    List<ModuleDescriptor> getModules();
}
