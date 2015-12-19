package com.dnfeitosa.codegraph.acceptance.web.controllers;

import com.dnfeitosa.codegraph.acceptance.support.DatabaseDependentAcceptanceTest;
import com.dnfeitosa.codegraph.web.controllers.ModuleController;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ModuleControllerAcceptanceTest extends DatabaseDependentAcceptanceTest {

    @Autowired
    private ModuleController moduleController;

    @Test
    public void shouldReturnAnApplicationsModule() {
        ResponseEntity<ModuleResource> response = moduleController.module("vraptor4", "vraptor-core");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getName(), is("vraptor-core"));
    }

    @Test
    public void shouldReturnNotFoundWhenAModuleDoesNotExist() {
        ResponseEntity<ModuleResource> response = moduleController.module("vraptor4", "non-existent-module");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    @Ignore("To be revisited on issue #11")
    public void shouldReturnNotFoundWhenApplicationDoesNotExist() {
        ResponseEntity<ModuleResource> response = moduleController.module("non-existent-app", "vraptor-core");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}