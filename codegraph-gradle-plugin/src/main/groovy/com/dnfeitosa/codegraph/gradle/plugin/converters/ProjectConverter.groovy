package com.dnfeitosa.codegraph.gradle.plugin.converters

import com.dnfeitosa.codegraph.gradle.plugin.client.resources.Artifact
import org.gradle.api.Project

class ProjectConverter {

    def Artifact toArtifact(Project project) {
        def artifact = new Artifact()
        artifact.name = project.name
        artifact.organization = project.group
        artifact.type = 'jar'
        artifact.extension = 'jar'
        artifact.version = project.version
        return artifact
    }
}
