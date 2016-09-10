package com.dnfeitosa.codegraph.gradle.plugin.converters

import com.dnfeitosa.codegraph.gradle.plugin.client.resources.Artifact
import org.gradle.api.artifacts.ResolvedDependency

class ResolvedDependencyConverter {

    def Artifact toArtifact(ResolvedDependency dependency) {
        def artifact = new Artifact()
        artifact.name = dependency.moduleName;
        artifact.organization = dependency.moduleGroup
        artifact.type = 'jar'
        artifact.extension = 'jar'
        artifact.version = dependency.moduleVersion;
        return artifact
    }
}
