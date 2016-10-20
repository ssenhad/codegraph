package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TypeResourceConverterTest {

    private TypeResourceConverter converter;

    @Before
    public void setUp() {
        converter = new TypeResourceConverter();
    }

    @Test
    public void shouldConvertAResourceToModel() {
        TypeResource type = new TypeResource();
        type.setName("TypeResourceConverterTest");
        type.setPackageName("com.dnfeitosa.codegraph.server.api.converters");
        type.setUsage("application");
        type.setType("class");

        Type node = converter.toModel(type);

        assertThat(node.getName(), is("TypeResourceConverterTest"));
        assertThat(node.getPackageName(), is("com.dnfeitosa.codegraph.server.api.converters"));
        assertThat(node.getUsage(), is("application"));
        assertThat(node.getType(), is("class"));
    }

    @Test
    public void shouldConvertAModelTypeToResource() {
        Type type = new Type("TypeResourceConverterTest", "com.dnfeitosa.codegraph.server.api.converters", "application", "class");

        TypeResource resource = converter.toResource(type);

        assertThat(resource.getName(), is("TypeResourceConverterTest"));
        assertThat(resource.getPackageName(), is("com.dnfeitosa.codegraph.server.api.converters"));
        assertThat(resource.getUsage(), is("application"));
        assertThat(resource.getType(), is("class"));
    }
}