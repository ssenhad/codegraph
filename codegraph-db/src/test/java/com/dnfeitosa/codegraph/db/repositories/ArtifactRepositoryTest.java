package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.Config;
import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.utils.ResultUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class } /*, locations = { "classpath:/codegraph-db-base.xml" /*, "classpath:/codegraph-db-test.xml" } */)
@ActiveProfiles("test")
@Transactional
public class ArtifactRepositoryTest {

    @Autowired
    private ArtifactRepository repository;

    @Test
    public void shouldSaveAnArtifact() {
        ArtifactNode node = new ArtifactNode(null, "artifact-name", "artifact-organization", "artifact-version", "artifact-type", "artifact-extension");
        node.addDependency(new ArtifactNode(null, "dependency-name", "dependency-organization", "dependency-version", "dependency-type", "dependency-extension"));

        repository.save(node);

        Iterable<ArtifactNode> all = repository.findAll();

        List<ArtifactNode> artifacts = ResultUtils.toList(all);
        assertThat(artifacts.size(), is(2));

        assertThat(artifacts.get(0).getDependencies().size(), is(1));
    }
}
