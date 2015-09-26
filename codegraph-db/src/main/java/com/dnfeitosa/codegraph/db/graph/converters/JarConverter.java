package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.db.graph.nodes.JarNode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.notNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class JarConverter {

	private static final Logger LOGGER = Logger.getLogger(JarConverter.class);

	public Set<JarNode> toNodes(List<Jar> dependencies) {
        return notNull(dependencies)
                .stream()
                .map(this::toNode)
                .collect(toSet());
	}

	public List<Jar> fromNodes(Set<JarNode> dependencies) {
        return notNull(dependencies)
                .stream()
                .map(this::fromNode)
                .collect(toList());
	}

	public JarNode toNode(Jar jar) {
		JarNode node = new JarNode();
		node.setOrganization(jar.getOrganization());
		node.setName(jar.getName());
		node.setVersion(jar.getVersion());
        node.prepare();
		return node;
	}

	public Jar fromNode(JarNode node) {
		LOGGER.trace(String.format("Converting node to jar '%s'", node.getName()));

		return new Jar(node.getOrganization(), node.getName(), node.getVersion());
	}
}
