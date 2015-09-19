package com.dnfeitosa.codegraph.web.controllers;

import static com.dnfeitosa.coollections.Coollections.$;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import com.dnfeitosa.codegraph.services.CodeGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dnfeitosa.coollections.Function;

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
		return $(codeGraph.applicationNames()).sortBy(natural()).toList();
	}

	private Function<String, String> natural() {
		return new Function<String, String>() {
			@Override
			public String apply(String name) {
				return name;
			}
		};
	}
}