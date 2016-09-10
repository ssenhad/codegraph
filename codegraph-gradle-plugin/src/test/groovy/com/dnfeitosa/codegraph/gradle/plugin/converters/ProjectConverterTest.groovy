package com.dnfeitosa.codegraph.gradle.plugin.converters

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class ProjectConverterTest {
    private ProjectConverter converter

    @Before
    def void setUp() {
        converter = new ProjectConverter()
    }

    @Test
    def void shouldConvertAProjectToArtifact() {
        def project = ProjectBuilder.builder()
                .withName('name')
                .build()
        project.group = 'organization'
        project.version = 'version'

        def artifact = converter.toArtifact(project)

        assertThat(artifact.name, is('name'))
        assertThat(artifact.organization, is('organization'))
        assertThat(artifact.version, is('version'))
        assertThat(artifact.type, is('jar'))
        assertThat(artifact.extension, is('jar'))
    }
}