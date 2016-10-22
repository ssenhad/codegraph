package com.dnfeitosa.codegraph.db.nodes.converters;

import com.dnfeitosa.codegraph.core.models.Field;
import com.dnfeitosa.codegraph.core.models.Method;
import com.dnfeitosa.codegraph.core.models.Parameter;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.db.nodes.FieldNode;
import com.dnfeitosa.codegraph.db.nodes.MethodNode;
import com.dnfeitosa.codegraph.db.nodes.ParameterNode;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.util.Arrays.asList;
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
        Type list = new Type("List", "java.util", "application", "class");
        Parameter parameter = new Parameter(1, list);
        type.addMethod(new Method("shouldConvertATypeToNode", asList(parameter), asList(list)));
        type.addField(new Field("converter", new Type("TypeNodeConverter", "com.dnfeitosa.codegraph.db.nodes.converters", "application", "class")));

        TypeNode node = converter.toNode(type);

        assertThat(node.getName(), is(name));
        assertThat(node.getType(), is(typeType));
        assertThat(node.getPackageName(), is(packageName));
        assertThat(node.getType(), is(typeType));

        List<FieldNode> fields = $(node.getFields()).toList();
        assertThat(fields.size(), is(1));
        assertThat(fields.get(0).getName(), is("converter"));
        assertThat(fields.get(0).getType().getName(), is("TypeNodeConverter"));

        List<MethodNode> methods = $(node.getMethods()).toList();
        assertThat(methods.size(), is(1));

        MethodNode methodNode = methods.get(0);
        assertThat(methodNode.getName(), is("shouldConvertATypeToNode"));

        List<ParameterNode> parameterNodes = $(methodNode.getParameters()).toList();
        assertThat(parameterNodes.size(), is(1));
        assertThat(parameterNodes.get(0).getOrder(), is(1));
        assertThat(parameterNodes.get(0).getType().getName(), is("List"));

        List<TypeNode> returnTypes = $(methodNode.getReturnTypes()).toList();
        assertThat(returnTypes.size(), is(1));

        assertThat(returnTypes.get(0).getName(), is("List"));
    }

    @Test
    public void shouldConvertANodeToModel() {
        TypeNode node = new TypeNode(name, packageName);
        node.setType(typeType);
        node.setUsage(usage);

        MethodNode methodNode = new MethodNode("shouldConvertANodeToModel");
        methodNode.addReturnType(new TypeNode("List", "java.util"));
        ParameterNode parameter = new ParameterNode();
        parameter.setOrder(1);
        parameter.setType(new TypeNode("List", "java.util"));
        parameter.setMethod(methodNode);
        methodNode.addParameter(parameter);
        node.addMethod(methodNode);

        FieldNode field = new FieldNode();
        field.setType(new TypeNode("TypeNodeConverter", "com.dnfeitosa.codegraph.db.nodes.converters"));
        field.setName("converter");
        node.addField(field);

        Type type = converter.toModel(node);

        assertThat(type.getName(), is(name));
        assertThat(type.getPackageName(), is(packageName));
        assertThat(type.getUsage(), is(usage));
        assertThat(type.getType(), is(typeType));

        List<Method> methods = type.getMethods();
        assertThat(methods.size(), is(1));

        Method method = methods.get(0);
        assertThat(method.getName(), is("shouldConvertANodeToModel"));
        assertThat(method.getReturnTypes().size(), is(1));
        assertThat(method.getReturnTypes().get(0).getName(), is("List"));

        assertThat(method.getParameters().size(), is(1));

        Parameter parameter1 = method.getParameters().get(0);
        assertThat(parameter1.getType().getName(), is("List"));
        assertThat(parameter1.getOrder(), is(1));
    }
}
