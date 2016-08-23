package com.dnfeitosa.codegraph.api.acceptance.services;

import com.dnfeitosa.codegraph.api.services.ArtifactService;
import com.dnfeitosa.codegraph.core.models.Artifact;
import com.dnfeitosa.codegraph.core.models.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration({ "classpath:/codegraph-db.xml", "classpath:/codegraph-acceptance-test.xml"})
@ActiveProfiles("acceptance")
@Transactional
public class ArtifactServiceTest {

    @Autowired
    private ArtifactService service;

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
}
