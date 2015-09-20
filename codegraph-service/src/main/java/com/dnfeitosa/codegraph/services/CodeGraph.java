package com.dnfeitosa.codegraph.services;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeGraph {

	private final ApplicationService applicationRepository;
	private final ModuleService moduleService;

	@Autowired
	public CodeGraph(ApplicationService applicationRepository, ModuleService moduleService) {
		this.applicationRepository = applicationRepository;
		this.moduleService = moduleService;
	}

	public List<Application> applications() {
		return applicationRepository.getAll();
	}

	public List<String> applicationNames() {
		return applicationRepository.getApplicationNames();
	}

	public Application getApplication(String name) {
		return applicationRepository.find(name);
	}

	public Module getModule(String name) {
		return moduleService.find(name);
	}
}
