package com.dnfeitosa.codegraph.core.descriptors.readers;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.loaders.finders.FileFinder;
import com.dnfeitosa.codegraph.testing.TestContext;
import com.dnfeitosa.coollections.Filter;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.dnfeitosa.codegraph.core.descriptors.DescriptorType.IVY;
import static com.dnfeitosa.coollections.Coollections.$;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultiApplicationReaderTest {

    private MultiApplicationReader reader;

    @Before
    public void setUp() {
        reader = new MultiApplicationReader(new MultiModuleApplicationReader(new FileFinder(new Terminal())));
    }

    @Test
    public void shouldReadADirectoryAnTakeEachDirectoryAsAnApplication() throws ReadException {
        Set<ApplicationDescriptor> appDescriptors = reader.readAt(TestContext.MULTIPLE_APPLICATIONS, IVY, input -> false);

        assertThat(appDescriptors.size(), is(2));
        assertThat($(appDescriptors).map(d -> d.getName()), hasItems("ivy-based-application", "another-ivy-based-application"));
    }

    @Test
    public void shouldIgnoreDirectoriesSpecifiedInTheFilter() throws ReadException {
        Filter<String> filter = input -> input.equals("ivy-based-application");
        Set<ApplicationDescriptor> appDescriptors = reader.readAt(TestContext.MULTIPLE_APPLICATIONS, IVY, filter);

        assertThat(appDescriptors.size(), is(1));
        assertThat($(appDescriptors).map(d -> d.getName()), hasItems("another-ivy-based-application"));
    }
}
