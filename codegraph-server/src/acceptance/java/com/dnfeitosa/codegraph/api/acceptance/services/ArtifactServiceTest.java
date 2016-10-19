package com.dnfeitosa.codegraph.server.api.acceptance.services;

import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Version;
import com.dnfeitosa.codegraph.db.Config;
import com.dnfeitosa.codegraph.server.api.services.ArtifactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Comparator.comparing;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class} /*, locations = { "classpath:/codegraph-db-base.xml", "classpath:/codegraph-acceptance-test.xml" } */)
@ComponentScan("com.dnfeitosa.codegraph")
@ActiveProfiles("acceptance")
@Transactional
public class ArtifactServiceTest {

    @Autowired
    private ArtifactService service;

    @Test
    public void shouldUpdateAnArtifactIfItAlreadyExists() {
        Artifact artifact = new Artifact("artifact-name", "artifact-organization", new Version("artifact-version"), "artifact-type", "artifact-extension");
        service.addArtifact(artifact);

        Artifact toUpdate = new Artifact("artifact-name", "artifact-organization", new Version("artifact-version"), "artifact-type", "artifact-extension");
        toUpdate.addDependency(new Artifact("dependency-name", "dependency-organization", new Version("dependency-version"), "dependency-type", "dependency-extension"));
        service.addArtifact(toUpdate);

        List<Artifact> artifacts = service.loadAll();
        artifacts.sort(comparing(Artifact::getName));

        assertThat(artifacts.size(), is(2));
        assertThat(artifacts.get(0).getName(), is("artifact-name"));
        assertThat(artifacts.get(1).getName(), is("dependency-name"));
    }

    @Test
    public void shouldLinkADependencyToAnExistingArtifact() {
        Artifact dependency = new Artifact("dependency-name", "dependency-organization", new Version("dependency-version"), "dependency-type", "dependency-extension");
        service.addArtifact(dependency);

        Artifact newArtifact = new Artifact("new-artifact-name", "new-artifact-organization", new Version("new-artifact-version"), "new-artifact-type", "new-artifact-extension");
        newArtifact.addDependency(new Artifact("dependency-name", "dependency-organization", new Version("dependency-version"), "dependency-type", "dependency-extension"));

        Artifact saved = service.addArtifact(newArtifact);
        assertNotNull(saved.getId());

        List<Artifact> artifacts = service.loadAll();
        artifacts.sort(comparing(Artifact::getName));

        assertThat(artifacts.size(), is(2));
        assertThat(artifacts.get(0).getName(), is("dependency-name"));
        assertThat(artifacts.get(1).getName(), is("new-artifact-name"));
    }

    @Test
    public void shouldUpdateTheArtifactInformationIfItExists() {
        Artifact mainArtifact = new Artifact("main-artifact", "organization", new Version("version"), "type", "extension");
        mainArtifact.addDependency(new Artifact("dependency1", "organization", new Version("version"), "type", "extension"));
        Artifact saved = service.addArtifact(mainArtifact);

        mainArtifact = new Artifact("main-artifact", "organization", new Version("version"), "type", "extension");
        mainArtifact.addDependency(new Artifact("dependency2", "organization", new Version("version"), "type", "extension"));
        service.addArtifact(mainArtifact);

        Artifact loaded = service.loadArtifact(saved.getId());
        assertThat(loaded.getDependencies().size(), is(2));
    }
}
