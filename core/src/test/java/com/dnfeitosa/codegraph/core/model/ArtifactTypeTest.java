package com.dnfeitosa.codegraph.core.model;

import org.junit.Test;

import static com.dnfeitosa.codegraph.core.model.ArtifactType.CLIAPP;
import static com.dnfeitosa.codegraph.core.model.ArtifactType.JAR;
import static com.dnfeitosa.codegraph.core.model.ArtifactType.WEBAPP;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactTypeTest {

	@Test
	public void shouldGetAnArtifactByItsName() {
		assertThat(ArtifactType.fromName("JAR"), is(JAR));
	}

	@Test
	public void shouldGetAnArtifactTypeByItsSubType() {
		assertThat(ArtifactType.fromName("TOMCAT-WAR"), is(WEBAPP));
		assertThat(ArtifactType.fromName("TOMCAT-AAR"), is(WEBAPP));
		assertThat(ArtifactType.fromName("ZIP"), is(CLIAPP));
		assertThat(ArtifactType.fromName("JAVAEXEC"), is(CLIAPP));
	}
}