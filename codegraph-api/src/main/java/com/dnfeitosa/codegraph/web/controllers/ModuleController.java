package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.services.ModuleService;
import com.dnfeitosa.codegraph.web.components.ResourceBuilders;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.dnfeitosa.codegraph.web.Responses.notFound;
import static com.dnfeitosa.codegraph.web.Responses.ok;

@Controller
public class ModuleController {

    private final ModuleService moduleService;
    private final ResourceBuilders resourceBuilders;

    @Autowired
    public ModuleController(ModuleService moduleService, ResourceBuilders resourceBuilders) {
        this.moduleService = moduleService;
        this.resourceBuilders = resourceBuilders;
    }

    @RequestMapping("/applications/{applicationName}/modules/{moduleName}")
    @ResponseBody
    public ResponseEntity<ModuleResource> module(@PathVariable("applicationName") String applicationName,
                                                 @PathVariable("moduleName") String moduleName) {
        Module module = moduleService.find(moduleName);
        return respondWith(module, applicationName);
    }

    private ResponseEntity<ModuleResource> respondWith(Module module, String applicationName) {
        if (module == null) {
            return notFound();
        }
        return ok(resourceBuilders.toResource(module, applicationName));
    }
}
