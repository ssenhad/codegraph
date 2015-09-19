package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import com.dnfeitosa.codegraph.core.model.Jar;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.notNull;

@Component
public class JarConverter {

	private static final Logger LOGGER = Logger.getLogger(JarConverter.class);

	public Set<com.dnfeitosa.codegraph.db.graph.nodes.Jar> toNodes(List<Jar> dependencies) {
		Set<com.dnfeitosa.codegraph.db.graph.nodes.Jar> jars = new HashSet<>();
		for (Jar dependency : notNull(dependencies)) {
			jars.add(toNode(dependency));
		}
		return jars;
	}

	public List<Jar> fromNodes(Set<com.dnfeitosa.codegraph.db.graph.nodes.Jar> dependencies) {
		List<Jar> jars = new ArrayList<>();
		for (com.dnfeitosa.codegraph.db.graph.nodes.Jar dependency : notNull(dependencies)) {
			jars.add(fromNode(dependency));
		}
		return jars;
	}

	public com.dnfeitosa.codegraph.db.graph.nodes.Jar toNode(Jar jar) {
		com.dnfeitosa.codegraph.db.graph.nodes.Jar node = new com.dnfeitosa.codegraph.db.graph.nodes.Jar();
		node.setOrganization(jar.getOrganization());
		node.setName(jar.getName());
		node.setVersion(jar.getVersion());
		return node;
	}

	public Jar fromNode(com.dnfeitosa.codegraph.db.graph.nodes.Jar node) {
		LOGGER.trace(String.format("Converting node to jar '%s'", node.getName()));

		return new Jar(node.getOrganization(), node.getName(), node.getVersion());
	}

	public List<Jar> toJar(Set<Module> modules) {
		List<Jar> jars = new ArrayList<>();
		for (Module module : notNull(modules)) {
			jars.add(new Jar("org", module.getName(), "${version}.+"));
		}
		return jars;
	}
}
