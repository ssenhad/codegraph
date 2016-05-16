package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.core.model.DependencyGraph;
import com.dnfeitosa.codegraph.services.ModuleService;
import com.dnfeitosa.codegraph.web.resources.GraphResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import com.dnfeitosa.codegraph.web.resources.builders.DependencyGraphResourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dnfeitosa.codegraph.web.Responses.ok;

@Controller
public class DependentsController {

    private ModuleService moduleService;
    private DependencyGraphResourceBuilder resourceBuilder;

    @Autowired
    public DependentsController(ModuleService moduleService, DependencyGraphResourceBuilder resourceBuilder) {
        this.moduleService = moduleService;
        this.resourceBuilder = resourceBuilder;
    }

    @RequestMapping("/applications/{applicationName}/modules/{moduleName}/dependents-graph")
    public ResponseEntity<GraphResource<ModuleResource>> dependents(@PathVariable("applicationName") String applicationName,
                                                                    @PathVariable("moduleName") String moduleName) {
        DependencyGraph dependencyGraph = moduleService.loadDependentsOf(moduleName);
        return ok(resourceBuilder.build(dependencyGraph, "dependents-graph"));
    }
}
