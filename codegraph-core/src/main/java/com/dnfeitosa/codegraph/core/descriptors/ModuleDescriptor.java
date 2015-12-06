package com.dnfeitosa.codegraph.core.descriptors;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;

import java.util.List;
import java.util.Set;

public interface ModuleDescriptor {
    String getName();
    String getLocation();
    String getOrganization();
    List<Jar> getDependencies(); // change to Set
    Set<ArtifactType> getExportTypes();
}
