package com.dnfeitosa.codegraph.descriptors.ivy;

import com.dnfeitosa.codegraph.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.model.ArtifactType;
import com.dnfeitosa.codegraph.model.Jar;

import java.util.List;
import java.util.Set;

public class IvyModuleDescriptor implements ModuleDescriptor {

    private IvyFile ivyFile;

    public IvyModuleDescriptor(IvyFile ivyFile) {
        this.ivyFile = ivyFile;
    }

    @Override
    public String getName() {
        return ivyFile.getModuleName();
    }

    @Override
    public String getLocation() {
        return ivyFile.getLocation();
    }

    @Override
    public List<Jar> getDependencies() {
        return ivyFile.getDependencies();
    }

    @Override
    public Set<ArtifactType> getExportTypes() {
        return ivyFile.getExportTypes();
    }
}
