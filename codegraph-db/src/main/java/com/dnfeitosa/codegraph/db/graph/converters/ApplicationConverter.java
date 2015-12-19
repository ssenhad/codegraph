package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class ApplicationConverter {

	private static final Logger LOGGER = Logger.getLogger(ApplicationConverter.class);

	private final ModuleConverter moduleConverter;

	@Autowired
	public ApplicationConverter(ModuleConverter moduleConverter) {
		this.moduleConverter = moduleConverter;
	}

	public Application fromNode(ApplicationNode node) {
		LOGGER.trace(String.format("Converting node to application '%s'.", node.getName()));

		List<Module> modules = moduleConverter.fromNodes(node.getModules());
		return new Application(node.getName(), modules);
	}

	public ApplicationNode toNode(Application application) {
		ApplicationNode node = new ApplicationNode();
		node.setName(application.getName());
		node.setModules(toNodes(application.getModules()));
		return node;
	}

	private Set<ModuleNode> toNodes(Collection<Module> modules) {
		return moduleConverter.toNodes(modules);
	}
}
