package com.dnfeitosa.codegraph.acceptance.web.controllers;

import com.dnfeitosa.codegraph.acceptance.support.DatabaseDependentAcceptanceTest;
import com.dnfeitosa.codegraph.web.controllers.ApplicationsController;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class ApplicationsControllerAcceptanceTest extends DatabaseDependentAcceptanceTest {

    @Autowired
    private ApplicationsController controller;

    @Test
    public void shouldReturnAllApplications() {
        List<ApplicationResource> applications = controller.applications();

        assertThat(applications.size(), is(2));
        assertThat(applications.stream().map(a -> a.getName()).collect(toList()), hasItems("vraptor4", "mirror"));
    }
}
