package com.dnfeitosa.codegraph.core.descriptors.maven;

import com.dnfeitosa.codegraph.core.filesystem.Path;
import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PomFileTest {

    private PomFile pomFile;

    @Before
    public void setUp() {
        pomFile = new PomFile(Path.join(TestContext.mirror().location(), "pom.xml"));
    }

    @Test
    public void shouldReturnTheModuleNameFromTheArtifactId() {
        assertThat(pomFile.getModuleName(), is("mirror"));
    }

    @Test
    public void shouldReturnTheDependencies() {
        assertThat(pomFile.getDependencies(), hasItems(
            jar("org.objenesis", "objenesis", "1.2"),
            jar("cglib", "cglib-nodep", "2.1_3"),
            jar("junit", "junit", "4.7")
        ));
    }

    @Test
    public void shouldReturnTheModuleExportTypesFromThePackaging() {
        assertThat(pomFile.getExportTypes(), hasItem(ArtifactType.JAR));
    }

    private Jar jar(String org, String name, String version) {
        return new Jar(org, name, version);
    }
}
