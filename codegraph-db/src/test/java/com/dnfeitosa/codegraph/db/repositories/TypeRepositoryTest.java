package com.dnfeitosa.codegraph.db.repositories;

import com.dnfeitosa.codegraph.db.nodes.FieldNode;
import com.dnfeitosa.codegraph.db.nodes.MethodNode;
import com.dnfeitosa.codegraph.db.nodes.ParameterNode;
import com.dnfeitosa.codegraph.db.nodes.TypeNode;
import com.dnfeitosa.codegraph.db.utils.ResultUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/codegraph-db-base.xml", "classpath:/codegraph-db-test.xml" })
@ActiveProfiles("test")
@Transactional
public class TypeRepositoryTest {

    @Autowired
    private TypeRepository repository;

    private TypeNode appType(String typeName, String packageName) {
        return type(typeName, packageName, "application");
    }

    private TypeNode type(String typeName, String packageName, String usage) {
        TypeNode type = new TypeNode(typeName, packageName);
        type.setUsage(usage);
        return type;
    }

    @Test
    public void shouldSaveAType() {
        TypeNode type = type("TypeRepositoryTest", "com.dnfeitosa.codegraph.db.repositories", "test");

        MethodNode method = new MethodNode(type.getQualifiedName(), "shouldSaveAType");

        ParameterNode parameter1 = new ParameterNode();
        parameter1.setOrder(0);
        parameter1.setType(appType("Object", "java.lang"));
        parameter1.setMethod(method);
        method.addParameter(parameter1);

        ParameterNode parameter2 = new ParameterNode();
        parameter2.setOrder(1);
        parameter2.setType(type("TypeRepositoryTest", "com.dnfeitosa.codegraph.db.repositories", "app"));
        parameter2.setMethod(method);
        method.addParameter(parameter2);

        FieldNode field = new FieldNode();
        field.setName("repository");
        field.setType(appType("TypeRepository", "com.dnfeitosa.codegraph.db.repositories"));
        type.addField(field);

        TypeNode returnType = appType("List", "java.util");

        method.addReturnType(returnType);

        type.addMethod(method);

        repository.save(type);

        List<TypeNode> types = ResultUtils.toList(repository.findAll());

        assertThat(types.size(), is(4));
    }
}
