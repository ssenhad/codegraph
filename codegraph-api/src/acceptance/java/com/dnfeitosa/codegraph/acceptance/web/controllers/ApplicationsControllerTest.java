package com.dnfeitosa.codegraph.acceptance.web.controllers;

import com.dnfeitosa.codegraph.acceptance.support.DatabaseDependentTest;
import com.dnfeitosa.codegraph.web.controllers.ApplicationsController;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class ApplicationsControllerTest extends DatabaseDependentTest {

    @Autowired
    private ApplicationsController controller;

    @Test
    public void shouldReturnAllApplications() {
        List<ApplicationResource> applications = controller.applications();

        ApplicationResource vraptor = new ApplicationResource("vraptor4");

        ApplicationResource mirror = new ApplicationResource("mirror");

        assertThat(applications.size(), is(2));
        assertThat(applications, hasItems(vraptor, mirror));
    }
}
