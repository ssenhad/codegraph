package com.dnfeitosa.codegraph.descriptors.readers;

import com.dnfeitosa.codegraph.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.descriptors.ModuleDescriptor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.codegraph.descriptors.DescriptorType.IVY;
import static com.dnfeitosa.codegraph.descriptors.DescriptorType.MAVEN;
import static com.dnfeitosa.codegraph.testing.TestContext.mirror;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class SingleModuleApplicationReaderTest {

    private String mirrorLocation = mirror().location();

    private ApplicationReader applicationReader;

    @Before
    public void setUp() {
        applicationReader = new SingleModuleApplicationReader();
    }

    @Test
    public void shouldDetectASingleModuleApplicationAtALocation() throws ReadException {
        Set<ApplicationDescriptor> appDescriptors = applicationReader.readAt(mirrorLocation, MAVEN);

        assertThat(appDescriptors.size(), is(1));

        ApplicationDescriptor appDescriptor = appDescriptors.stream().findFirst().get();
        assertThat(appDescriptor.getName(), is(mirror().name()));

        List<ModuleDescriptor> modules = appDescriptor.getModules();
        assertThat(modules.size(), is(1));
        assertThat(modules.get(0).getName(), is("mirror"));
    }

    @Test(expected = ReadException.class)
    public void shouldThrowExceptionWhenApplicationDescriptorIsNotPresentAtExpectedLocation() throws ReadException {
        applicationReader.readAt(mirrorLocation, IVY);
    }
}
