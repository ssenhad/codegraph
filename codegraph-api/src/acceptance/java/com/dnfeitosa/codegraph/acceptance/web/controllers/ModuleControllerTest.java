package com.dnfeitosa.codegraph.acceptance.web.controllers;

import com.dnfeitosa.codegraph.acceptance.support.DatabaseDependentTest;
import com.dnfeitosa.codegraph.web.controllers.ModuleController;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ModuleControllerTest extends DatabaseDependentTest {

    @Autowired
    private ModuleController moduleController;

    public void shouldReturnAnApplicationsModule() {

    }

    @Test
    public void shouldReturn404WhenAModuleIsNotFound() {
        ResponseEntity<ModuleResource> response = moduleController.module("non-existent-app", "non-existent-module");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

}