package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.services.ApplicationService;
import com.dnfeitosa.codegraph.web.components.ResourceBuilders;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.dnfeitosa.codegraph.web.Responses.notFound;
import static com.dnfeitosa.codegraph.web.Responses.ok;

@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
	private final ResourceBuilders resourceBuilders;

	@Autowired
    public ApplicationController(ApplicationService applicationService,
                                 ResourceBuilders resourceBuilders) {
        this.applicationService = applicationService;
        this.resourceBuilders = resourceBuilders;
	}

	@RequestMapping("/applications/{name}")
	@ResponseBody
	public ResponseEntity<ApplicationResource> application(@PathVariable("name") String name) {
        Application application = applicationService.find(name);
        return respond(application);
    }

	private ResponseEntity<ApplicationResource> respond(Application application) {
		if (application == null) {
			return notFound();
		}
		return ok(toResource(application));
	}

	private ApplicationResource toResource(Application application) {
		return resourceBuilders.toResource(application);
	}
}
