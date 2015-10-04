package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.db.graph.repositories.ApplicationRepository;
import com.dnfeitosa.codegraph.web.resources.ApplicationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ApplicationsController {

    private final ApplicationRepository applicationRepository;

    @Autowired
	public ApplicationsController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

	@RequestMapping(value = "/applications", method = GET)
	@ResponseBody
	public List<ApplicationResource> applications() {
        return $(applicationRepository.getApplicationNames())
            .map(applicationName -> new ApplicationResource(applicationName));
	}
}