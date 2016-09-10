package com.dnfeitosa.codegraph.gradle.plugin.client.resources

class Artifact {

    String name
    String organization
    String version
    String extension
    String type
    List<Artifact> dependencies = []

    void addDependency(Artifact dependency) {
        dependencies.add(dependency)
    }
}
