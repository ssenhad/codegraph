package com.dnfeitosa.codegraph.acceptance.web.controllers;

import com.dnfeitosa.codegraph.acceptance.support.DatabaseDependentTest;
import com.dnfeitosa.codegraph.web.controllers.ApplicationController;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApplicationControllerTest extends DatabaseDependentTest {

    @Autowired
    private ApplicationController applicationController;

    @Test
    public void shouldReturnAnApplication() {
        ResponseEntity<ApplicationResource> response = applicationController.application("vraptor4");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ApplicationResource resource = response.getBody();
        assertThat(resource.getUri(), is("/applications/vraptor4"));
        assertThat(resource.getName(), is("vraptor4"));

        List<ModuleResource> modules = resource.getModules();
        modules.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));

        assertThat(modules.size(), is(2));
        ModuleResource core = modules.get(0);
        assertThat(core.getName(), is("vraptor-core"));
        assertThat(core.getUri(), is("/applications/vraptor4/modules/vraptor-core"));

        ModuleResource site = modules.get(1);
        assertThat(site.getName(), is("vraptor-site"));
        assertThat(site.getUri(), is("/applications/vraptor4/modules/vraptor-site"));
    }

    @Test
    public void shouldReturn404WhenApplicationIsNotFound() {
        ResponseEntity<ApplicationResource> response = applicationController.application("dont-exist");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}
