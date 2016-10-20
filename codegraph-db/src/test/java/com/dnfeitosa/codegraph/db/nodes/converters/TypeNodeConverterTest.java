package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TypeNodeConverterTest {

    private String name = "TypeNodeConverterTest";
    private String packageName = "com.dnfeitosa.codegraph.db.nodes.converters";
    private String usage = "application";
    private String typeType = "class";
    private TypeNodeConverter converter;

    @Before
    public void setUp() {
        converter = new TypeNodeConverter();
    }

    @Test
    public void shouldConvertATypeToNode() {

        Type type = new Type(name, packageName, usage, typeType);

        TypeNode node = converter.toNode(type);

        assertThat(node.getName(), is(name));
        assertThat(node.getType(), is(typeType));
        assertThat(node.getPackageName(), is(packageName));
        assertThat(node.getType(), is(typeType));
    }

    @Test
    public void shouldConvertANodeToModel() {
        TypeNode node = new TypeNode(name, packageName);
        node.setType(typeType);
        node.setUsage(usage);

        Type type = converter.toModel(node);

        assertThat(type.getName(), is(name));
        assertThat(type.getPackageName(), is(packageName));
        assertThat(type.getUsage(), is(usage));
        assertThat(type.getType(), is(typeType));
    }
}
