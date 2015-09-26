package com.dnfeitosa.codegraph.web.controllers;

import com.dnfeitosa.codegraph.services.CodeGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ApplicationsController {

	private final CodeGraph codeGraph;

	@Autowired
	public ApplicationsController(CodeGraph codeGraph) {
		this.codeGraph = codeGraph;
	}

	@RequestMapping(value = "/applications", method = GET)
	@ResponseBody
	public List<String> applications() {
        List<String> names = codeGraph.applicationNames();
        names.sort(Comparator.<String>naturalOrder());
        return names;
	}
}