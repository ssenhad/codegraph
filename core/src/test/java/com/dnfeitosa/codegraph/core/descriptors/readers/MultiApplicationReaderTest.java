package com.dnfeitosa.codegraph.core.descriptors.readers;

import com.dnfeitosa.codegraph.core.commandline.Terminal;
import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.loaders.finders.FileFinder;
import com.dnfeitosa.codegraph.testing.TestContext;
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
        Set<ApplicationDescriptor> appDescriptors = reader.readAt(TestContext.MULTIPLE_APPLICATIONS, IVY);

        assertThat(appDescriptors.size(), is(2));
        assertThat($(appDescriptors).map(d -> d.getName()), hasItems("ivy-based-application", "another-ivy-based-application"));
    }
}
