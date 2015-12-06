package com.dnfeitosa.codegraph.core.descriptors.maven;

import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;

import java.util.List;
import java.util.Set;

public class MavenModuleDescriptor implements ModuleDescriptor {

    private PomFile pomFile;

    public MavenModuleDescriptor(PomFile pomFile) {
        this.pomFile = pomFile;
    }

    @Override
    public String getName() {
        return pomFile.getModuleName();
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public String getOrganization() {
        return pomFile.getOrganization();
    }

    @Override
    public List<Jar> getDependencies() {
        return pomFile.getDependencies();
    }

    @Override
    public Set<ArtifactType> getExportTypes() {
        return pomFile.getExportTypes();
    }
}
