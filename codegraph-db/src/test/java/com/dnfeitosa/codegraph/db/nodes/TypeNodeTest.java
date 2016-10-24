package com.dnfeitosa.codegraph.db.nodes;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TypeNodeTest {

    @Test
    public void qualifiedNameIncludesPackageWhenItIsPresent() {
        TypeNode type = new TypeNode("TypeName", "com.dnfeitosa.codegraph");

        assertThat(type.getQualifiedName(), is("com.dnfeitosa.codegraph.TypeName"));
    }

    @Test
    public void useTypeNameAsQualifiedNameWhenPackageIsNotPresent() {
        assertThat(new TypeNode("int").getQualifiedName(), is("int"));
        assertThat(new TypeNode("void", null).getQualifiedName(), is("void"));
    }
}
