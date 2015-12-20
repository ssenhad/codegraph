package com.dnfeitosa.codegraph.web.components;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import com.dnfeitosa.codegraph.web.resources.ModuleResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceBuilders {

	public ApplicationResource toResource(final Application application) {
        ApplicationResource resource = new ApplicationResource(application.getName());
        resource.setModules(getModuleNames(application));
        return resource;
	}

	private List<ModuleResource> getModuleNames(Application application) {
        return application.getModules()
                .stream()
                .map(module -> new ModuleResource(new ApplicationResource(application.getName()), module.getName()))
                .collect(Collectors.toList());
	}
}
