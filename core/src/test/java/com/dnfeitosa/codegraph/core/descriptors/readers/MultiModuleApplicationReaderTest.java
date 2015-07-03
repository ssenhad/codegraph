package com.dnfeitosa.codegraph.core.descriptors.readers;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.core.loaders.finders.FileFinder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.dnfeitosa.codegraph.core.descriptors.DescriptorType.MAVEN;
import static com.dnfeitosa.codegraph.testing.TestContext.vraptor;
import static com.dnfeitosa.coollections.Coollections.$;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultiModuleApplicationReaderTest {

    private String vraptorLocation = vraptor().location();
    private MultiModuleApplicationReader applicationReader;

    @Before
    public void setUp() {
        applicationReader = new MultiModuleApplicationReader(new FileFinder(new Terminal()));
    }

    @Test
    public void shouldReadAMultiModuleApplicationAtALocation() throws ReadException {
        Set<ApplicationDescriptor> appDescriptors = applicationReader.readAt(vraptorLocation, MAVEN);

        assertThat(appDescriptors.size(), is(1));

        ApplicationDescriptor appDescriptor = appDescriptors.stream().findFirst().get();
        assertThat(appDescriptor.getName(), is(vraptor().name()));

        List<ModuleDescriptor> modules = appDescriptor.getModules();
        assertThat(modules.size(), is(3));
        System.out.println($(modules).map(m -> m.getName()));
        assertThat($(modules).map(m -> m.getName()), hasItems("vraptor", "vraptor-musicjungle", "vraptor-blank-project"));
    }
}