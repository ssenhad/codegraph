package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.core.model.DependencyGraph;
import com.dnfeitosa.codegraph.services.ModuleService;
import com.dnfeitosa.codegraph.web.components.ResourceBuilders;
import com.dnfeitosa.codegraph.web.resources.GraphResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import com.dnfeitosa.codegraph.web.resources.builders.DependencyGraphResourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.dnfeitosa.codegraph.web.Responses.ok;

@Controller
public class DependenciesController {

    private ModuleService moduleService;
    private DependencyGraphResourceBuilder resourceBuilder;
    private ResourceBuilders resourceBuilders;

    @Autowired
    public DependenciesController(ModuleService moduleService, DependencyGraphResourceBuilder resourceBuilder) {
        this.moduleService = moduleService;
        this.resourceBuilder = resourceBuilder;
    }

    @RequestMapping("/applications/{applicationName}/modules/{moduleName}/dependency-graph")
    @ResponseBody
    public ResponseEntity<GraphResource<ModuleResource>> dependencies(
            @PathVariable("applicationName") String applicationName,
            @PathVariable("moduleName") String moduleName) {

        DependencyGraph dependencyGraph = moduleService.loadDependenciesOf(moduleName);
        return respondWith(applicationName, moduleName, dependencyGraph);
    }

    private ResponseEntity<GraphResource<ModuleResource>> respondWith(
            String applicationName, String moduleName, DependencyGraph dependencyGraph) {

        GraphResource<ModuleResource> resource = resourceBuilder.build(applicationName, dependencyGraph);
        return ok(resource);
    }
}
