package com.dnfeitosa.codegraph.web.components;

import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.springframework.stereotype.Component;

@Component
public class ModuleResourceBuilder {

    public ModuleResource toResource(Module module, String applicationName) {
        ModuleResource resource = new ModuleResource(applicationName, module.getName());
        resource.setOrganization(module.getOrganization());
        resource.setVersion(module.getVersion());
        return resource;
    }

    public ModuleResource toResource(Module module) {
        String applicationName = module.getApplication().getName();
        ModuleResource resource = toResource(module, applicationName);
        resource.setParent(new ApplicationResource(applicationName));
        return resource;
    }
}
