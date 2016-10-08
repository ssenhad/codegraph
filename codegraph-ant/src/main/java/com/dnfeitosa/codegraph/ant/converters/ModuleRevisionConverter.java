package com.dnfeitosa.codegraph.ant.converters;

import com.dnfeitosa.codegraph.client.resources.Artifact;
import org.apache.ivy.core.module.id.ModuleRevisionId;

public class ModuleRevisionConverter {

    public Artifact toArtifact(ModuleRevisionId module) {
        Artifact artifact = new Artifact();
        artifact.setName(module.getName());
        artifact.setOrganization(module.getOrganisation());
        artifact.setVersion(module.getRevision());
        artifact.setType("jar");
        artifact.setExtension("jar");
        return artifact;
    }
}
