package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Module;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ModuleConverterTest {

    private final String moduleName = "moduleName";
    private final ModuleNode node = new ModuleNode() {{
        setName(moduleName);
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
    }

    @Test
    public void shouldReturnNullWhenModuleNodeIsNull() {
        assertNull(moduleConverter.fromNode(null));
    }

}