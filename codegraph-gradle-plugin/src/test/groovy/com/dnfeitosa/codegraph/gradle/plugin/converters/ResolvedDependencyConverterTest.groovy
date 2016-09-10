package com.dnfeitosa.codegraph.gradle.plugin.converters

import org.gradle.api.internal.artifacts.DefaultModuleVersionIdentifier
import org.gradle.api.internal.artifacts.DefaultResolvedDependency
import org.junit.Before
import org.junit.Test

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ResolvedDependencyConverterTest {
    private ResolvedDependencyConverter converter

    @Before
    def void setUp() {
        converter = new ResolvedDependencyConverter()
    }

    @Test
    def void shouldConvertAProjectToArtifact() {
        def dependency = new DefaultResolvedDependency(new DefaultModuleVersionIdentifier("organization", "name", "version"), "x")

        def artifact = converter.toArtifact(dependency)

        assertThat(artifact.name, is('name'))
        assertThat(artifact.organization, is('organization'))
        assertThat(artifact.version, is('version'))
        assertThat(artifact.type, is('jar'))
        assertThat(artifact.extension, is('jar'))
    }
}