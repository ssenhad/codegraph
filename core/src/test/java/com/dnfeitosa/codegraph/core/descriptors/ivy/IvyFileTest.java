package com.dnfeitosa.codegraph.core.descriptors.ivy;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.codegraph.core.model.ArtifactType.CONFIG;
import static com.dnfeitosa.codegraph.core.model.ArtifactType.JAR;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IvyFileTest {

	private IvyFile ivyFile;

	@Before
	public void setUp() {
		ivyFile = new IvyFile(TestContext.descriptorLocation("ivy.xml"));
	}

	@Test
	public void shouldReturnTheModuleNameFromTheInfoTag() {
		assertThat(ivyFile.getModuleName(), is("IvyDescriptorExample"));
	}

	@Test
	public void shouldReturnTheIvyFileDirectoryAsTheLocation() {
		assertThat(ivyFile.getLocation(), is(TestContext.MODULE_DESCRIPTORS));
	}

	@Test
	public void shouldReturnTheJarDependenciesDeclaredInTheFile() {
		List<Jar> dependencies = ivyFile.getDependencies();

		assertThat(dependencies.size(), is(3));

		Jar commonsLogging = jar("apache", "commons-logging", "1.0.3");
		Jar java2ee = jar("java", "j2ee", "1.3");
		Jar struts = jar("apache", "struts", "1.1");

		assertThat(dependencies, hasItems(commonsLogging, java2ee, struts));
	}

	@Test
	public void shouldReturnTheModuleExportTypes() {
		Set<ArtifactType> exportTypes = ivyFile.getExportTypes();

		assertThat(exportTypes, hasItems(JAR, CONFIG));
	}

	private Jar jar(String organization, String name, String version) {
		return new Jar(organization, name, version);
	}
}
