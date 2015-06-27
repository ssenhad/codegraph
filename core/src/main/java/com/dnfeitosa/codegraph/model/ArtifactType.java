package com.dnfeitosa.codegraph.model;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

public enum ArtifactType {

	UNKNOWN, SOURCE, DOC("JAVADOC"), WSDL("SCHEMAS", "WSDL"), COOKBOOK, SERVER, CONFIG, TEST("FUNCTIONALTEST"), JAR, WEBAPP(
			"TOMCAT-AAR", "TOMCAT-WAR", "EAR", "WAR", "AAR"), CLIAPP("ZIP", "JAVAEXEC");

	private static final Logger LOGGER = Logger.getLogger(ArtifactType.class);

	private Set<String> subTypes;

	ArtifactType(String... subTypes) {
		this.subTypes = new HashSet<>(Arrays.asList(subTypes));
	}

	private boolean isSubType(String value) {
		return subTypes.contains(value);
	}

	public static ArtifactType fromName(String value) {
		for (ArtifactType artifactType : values()) {
			if (matches(value, artifactType)) {
				return artifactType;
			}
		}

		LOGGER.warn(format("Unknown artifact type '%s'.", value));
		return UNKNOWN;
	}

	private static boolean matches(String value, ArtifactType artifactType) {
		return artifactType.name().equalsIgnoreCase(value) || artifactType.isSubType(value);
	}
}
