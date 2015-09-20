package com.dnfeitosa.codegraph.services;

import com.dnfeitosa.codegraph.db.graph.converters.ApplicationConverter;
import com.dnfeitosa.codegraph.db.graph.repositories.ApplicationRepository;
import com.dnfeitosa.coollections.Function;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.lang.String.format;

@Service
public class ApplicationService {

	private static final Logger LOGGER = Logger.getLogger(ApplicationService.class);

	private final ApplicationRepository applicationRepository;
	private final ApplicationConverter converter;

	@Autowired
	public ApplicationService(ApplicationRepository applicationRepository, ApplicationConverter converter) {
		this.applicationRepository = applicationRepository;
		this.converter = converter;
	}

	public void save(com.dnfeitosa.codegraph.core.model.Application application) {
		LOGGER.info(format("Saving '%s'.", application.getName()));
		com.dnfeitosa.codegraph.db.graph.nodes.Application node = converter.toNode(application);
		applicationRepository.save(node);
	}

	public List<String> getApplicationNames() {
		return applicationRepository.getApplicationNames();
	}

	public com.dnfeitosa.codegraph.core.model.Application find(String name) {
		com.dnfeitosa.codegraph.db.graph.nodes.Application node = applicationRepository.findBySchemaPropertyValue("name", name);
		return converter.fromNode(node);
	}

	public List<com.dnfeitosa.codegraph.core.model.Application> getAll() {
		Result<com.dnfeitosa.codegraph.db.graph.nodes.Application> all = applicationRepository.findAll();
		return $(all).map(toModel()).toList();
	}

	private Function<com.dnfeitosa.codegraph.db.graph.nodes.Application, com.dnfeitosa.codegraph.core.model.Application> toModel() {
		return input -> converter.fromNode(input);
	}
}