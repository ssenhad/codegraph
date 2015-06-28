package com.dnfeitosa.codegraph.descriptors;

import com.dnfeitosa.codegraph.model.ArtifactType;
import com.dnfeitosa.codegraph.model.Jar;

import java.util.List;
import java.util.Set;

public interface ModuleDescriptor {
    String getName();
    String getLocation();
    List<Jar> getDependencies(); // change to Set
    Set<ArtifactType> getExportTypes();
}
