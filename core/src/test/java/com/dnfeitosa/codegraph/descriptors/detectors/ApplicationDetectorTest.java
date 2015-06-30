package com.dnfeitosa.codegraph.descriptors.detectors;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.loaders.finders.FileFinder;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.dnfeitosa.codegraph.descriptors.DescriptorTypes.IVY;
import static com.dnfeitosa.codegraph.descriptors.DescriptorTypes.MAVEN;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class ApplicationDetectorTest {

    private ApplicationDetector applicationDetector;

    @Before
    public void setUp() {
        applicationDetector = new ApplicationDetector(new FileFinder(new Terminal()));
    }

    @Test
    public void shouldDetectAnIvyApplicationAtALocation() {
        String location = TestContext.ivyBased().location();
        ApplicationDescriptor applicationDescriptor = applicationDetector.detectAt(location, IVY);

        assertThat(applicationDescriptor.getName(), is(TestContext.ivyBased().name()));

        List<ModuleDescriptor> modules = applicationDescriptor.getModules();
        assertThat(modules.size(), is(2));
        assertThat(modules.get(0).getName(), is("module1"));
        assertThat(modules.get(1).getName(), is("module2"));
    }

    @Test
    public void shouldDetectAMavenApplicationAtALocation() {
        String location = TestContext.mirror().location();
        ApplicationDescriptor applicationDescriptor = applicationDetector.detectAt(location, MAVEN);

        assertThat(applicationDescriptor.getName(), is(TestContext.mirror().name()));

        List<ModuleDescriptor> modules = applicationDescriptor.getModules();
        assertThat(modules.size(), is(2));
        assertThat(modules.get(0).getName(), is("mirror-packaged-test"));
        assertThat(modules.get(1).getName(), is("mirror"));
    }
}