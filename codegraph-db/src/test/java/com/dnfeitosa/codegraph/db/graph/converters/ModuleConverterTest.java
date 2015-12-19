package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ApplicationNode;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ModuleConverterTest {

    private final String applicationName = "applicationName";
    private final ApplicationNode applicationNode = new ApplicationNode() {{
        setName(applicationName);
    }};

    private final String moduleName = "moduleName";
    private final ModuleNode node = new ModuleNode() {{
        setName(moduleName);
        setApplication(applicationNode);
    }};

    private ModuleConverter moduleConverter;

    @Before
    public void setUp() {
        moduleConverter = new ModuleConverter(new JarConverter(), new ArtifactConverter());
    }

    @Test
    public void shouldConvertAModuleNodeToModule() {
        Module module = moduleConverter.fromNode(node);

        assertThat(module.getName(), is(moduleName));
//        assertThat(module.getApplication().getName(), is(applicationName));
    }

    @Test
    @Ignore
    public void doesNotFailWhenApplicationInfoIsNotPresent() {
        node.setApplication(null);

        Module module = moduleConverter.fromNode(node);

        assertThat(module.getName(), is(moduleName));
        assertNull(module.getApplication());
    }

    @Test
    public void shouldReturnNullWhenModuleNodeIsNull() {
        assertNull(moduleConverter.fromNode(null));
    }
}