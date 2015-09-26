package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.web.resources.ImpactGraphResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnfeitosa.codegraph.web.resources.ImpactZoneResource;

@Controller
public class ImpactZoneController {

	@RequestMapping("/modules/{moduleName}/impact-zone")
	public ResponseEntity<ImpactZoneResource> impactZone(@PathVariable("moduleName") String moduleName) {
		return null;
	}

	@RequestMapping("/modules/{moduleName}/impact-graph")
	public ResponseEntity<ImpactGraphResource> impactGraph(@PathVariable("moduleName") String moduleName) {
		return null;
	}
}
