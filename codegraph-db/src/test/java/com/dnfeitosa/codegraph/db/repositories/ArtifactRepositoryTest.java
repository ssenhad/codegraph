package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.nodes.ArtifactNode;
import com.dnfeitosa.codegraph.db.utils.ResultUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-db-base.xml", "classpath:/codegraph-db-test.xml" })
@ActiveProfiles("test")
@Transactional
public class ArtifactRepositoryTest {

    @Autowired
    private ArtifactRepository repository;

    @Test
    public void shouldSaveAnArtifact() {
        ArtifactNode node = new ArtifactNode(null, "artifact-name", "artifact-organization", "artifact-version", "artifact-type", "artifact-extension");

        repository.save(node);

        Result<ArtifactNode> all = repository.findAll();

        List<ArtifactNode> artifacts = ResultUtils.toList(all);
        assertThat(artifacts.size(), is(1));
    }
}
