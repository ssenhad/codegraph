package com.dnfeitosa.codegraph.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dnfeitosa.codegraph.web.resources.DependenciesResource;

@Controller
public class DependenciesController {

	@RequestMapping("/modules/{moduleName}/dependencies")
	public ResponseEntity<DependenciesResource> dependencies(@PathVariable("moduleName") String moduleName,
			@RequestParam(value = "orgOnly", defaultValue = "false") boolean orgOnly) {
		return null;
	}
}
