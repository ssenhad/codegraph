package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.nodes.ProjectNode;
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
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository repository;

    @Test
    public void shouldSaveAProject() {
        ProjectNode node = new ProjectNode(null, "project-name", "project-organization", "project-version");

        repository.save(node);

        Result<ProjectNode> all = repository.findAll();

        List<ProjectNode> projects = ResultUtils.toList(all);
        assertThat(projects.size(), is(1));
    }
}
