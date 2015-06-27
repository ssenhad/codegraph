package com.dnfeitosa.codegraph.services;

import com.dnfeitosa.codegraph.model.Application;
import com.dnfeitosa.codegraph.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeGraph {

	private final ApplicationService applicationRepository;
	private final ModuleRepository moduleRepository;

	@Autowired
	public CodeGraph(ApplicationService applicationRepository, ModuleRepository moduleRepository) {
		this.applicationRepository = applicationRepository;
		this.moduleRepository = moduleRepository;
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
		return moduleRepository.find(name);
	}
}
