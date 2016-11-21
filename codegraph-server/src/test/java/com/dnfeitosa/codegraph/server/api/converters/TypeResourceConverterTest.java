package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Field;
import com.dnfeitosa.codegraph.core.models.Method;
import com.dnfeitosa.codegraph.core.models.Parameter;
import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.server.api.resources.FieldResource;
import com.dnfeitosa.codegraph.server.api.resources.MethodResource;
import com.dnfeitosa.codegraph.server.api.resources.ParameterResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TypeResourceConverterTest {

    private TypeResourceConverter converter;

    @Before
    public void setUp() {
        converter = new TypeResourceConverter();
    }

    @Test
    public void shouldConvertATypeResourceToModel() {
        TypeResource type = new TypeResource();
        type.setName("TypeResourceConverterTest");
        type.setPackageName("com.dnfeitosa.codegraph.server.api.converters");
        type.setUsage("application");
        type.setType("class");
        type.setSuperclass(new TypeResource() {{
            setName("Object");
            setPackageName("java.lang");
        }});
        type.setInterfaces(asList(new TypeResource() {{
            setName("Iterator");
            setPackageName("java.lang");
        }}));

        MethodResource methodResource = new MethodResource("shouldConvertAResourceToModel");
        TypeResource list = type("List", "java.util");
        methodResource.setParameters(asList(new ParameterResource(list, 1)));

        methodResource.setReturnTypes(asList(list));

        type.setMethods(asList(methodResource));

        FieldResource fieldResource = new FieldResource("converter", type("TypeResourceConverter", "com.dnfeitosa.codegraph.server.api.converters"));
        type.setFields(asList(fieldResource));

        Type node = converter.toModel(type);

        assertThat(node.getName(), is("TypeResourceConverterTest"));
        assertThat(node.getPackageName(), is("com.dnfeitosa.codegraph.server.api.converters"));
        assertThat(node.getUsage(), is("application"));
        assertThat(node.getType(), is("class"));

        assertThat(node.getSuperclass().getName(), is("Object"));

        List<Type> interfaces = node.getInterfaces();
        assertThat(interfaces.size(), is(1));
        assertThat(interfaces.get(0).getName(), is("Iterator"));

        List<Method> methods = node.getMethods();
        assertThat(methods.size(), is(1));

        Method method = methods.get(0);
        assertThat(method.getName(), is("shouldConvertAResourceToModel"));
        assertThat(method.getParameters().size(), is(1));

        Parameter parameter = method.getParameters().get(0);
        assertThat(parameter.getOrder(), is(1));
        assertThat(parameter.getType().getName(), is("List"));

        List<Type> returnTypes = method.getReturnTypes();
        assertThat(returnTypes.size(), is(1));
        assertThat(returnTypes.get(0).getName(), is("List"));

        List<Field> fields = node.getFields();
        assertThat(fields.size(), is(1));
        assertThat(fields.get(0).getName(), is("converter"));
        assertThat(fields.get(0).getType().getName(), is("TypeResourceConverter"));
    }

    private TypeResource type(String name, String packageName) {
        TypeResource type = new TypeResource();
        type.setName(name);
        type.setPackageName(packageName);
        type.setUsage("application");
        type.setType("class");
        return type;
    }

    @Test
    public void shouldConvertAModelTypeToResource() {
        Type list = new Type("List", "java.util", "application", "class");

        Type type = new Type("TypeResourceConverterTest", "com.dnfeitosa.codegraph.server.api.converters", "application", "class");
        type.addMethod(new Method("shouldConvertAModelTypeToResource", asList(new Parameter(1, list)), asList(list)));
        type.addField(new Field("converter", new Type("TypeResourceConverter", "com.dnfeitosa.codegraph.server.api.converters", "application", "class")));
        type.setSuperclass(new Type("Object", "java.lang", "application", "class"));
        type.addInterface(new Type("Iterator", "java.util", "application", "class"));

        TypeResource resource = converter.toResource(type);

        assertThat(resource.getName(), is("TypeResourceConverterTest"));
        assertThat(resource.getPackageName(), is("com.dnfeitosa.codegraph.server.api.converters"));
        assertThat(resource.getUsage(), is("application"));
        assertThat(resource.getType(), is("class"));

        assertThat(resource.getSuperclass().getName(), is("Object"));
        assertThat(resource.getInterfaces().size(), is(1));
        assertThat(resource.getInterfaces().get(0).getName(), is("Iterator"));

        List<MethodResource> methods = resource.getMethods();
        assertThat(methods.size(), is(1));

        MethodResource methodResource = methods.get(0);
        assertThat(methodResource.getName(), is("shouldConvertAModelTypeToResource"));
        assertThat(methodResource.getParameters().size(), is(1));
        assertThat(methodResource.getParameters().get(0).getType().getName(), is("List"));

        assertThat(methodResource.getReturnTypes().size(), is(1));
        assertThat(methodResource.getReturnTypes().get(0).getName(), is("List"));

        List<FieldResource> fields = resource.getFields();
        assertThat(fields.size(), is(1));
        assertThat(fields.get(0).getName(), is("converter"));
        assertThat(fields.get(0).getType().getName(), is("TypeResourceConverter"));
    }
}