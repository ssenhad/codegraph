package com.dnfeitosa.codegraph.server.acceptance;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AcceptanceTestConfiguration.class)
@TestPropertySource({ "classpath:/acceptance-test.properties" })
@Ignore
public class AcceptanceTestBase {
}
