package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.UnitTestConfiguration;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfiguration.class)
@TestPropertySource({ "classpath:/unit-test.properties" })
@Ignore
public class UnitTestBase {
}
