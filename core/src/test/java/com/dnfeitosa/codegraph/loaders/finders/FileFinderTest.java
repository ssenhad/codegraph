package com.dnfeitosa.codegraph.loaders.finders;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.testing.TestContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileFinderTest {

    private FileFinder fileFinder;

    @Before
    public void setUp() {
        fileFinder = new FileFinder(new Terminal());
    }

    @Test
    public void shouldFindTheFilesOnAGivenLocation() {
        List<String> files = fileFinder.find(TestContext.ivyBased().location(), "ivy.xml");

        assertThat(files.size(), is(2));
    }
}