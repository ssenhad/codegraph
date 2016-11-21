package com.dnfeitosa.codegraph.db.nodes;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@TypeAlias("Type")
public class TypeNode {

    @GraphId
    @Indexed
    private Long id;

    @Indexed(unique = true)
    private String qualifiedName;
    private String name;

    @GraphProperty(propertyName = "package")
    private String packageName;
    private String usage;
    private String type;

    @RelatedTo(direction = Direction.OUTGOING, type = "USES")
    private Set<TypeNode> referencedTypes;

    @RelatedTo(direction = Direction.OUTGOING, type = "DECLARES", enforceTargetType = true)
    private Set<FieldNode> fields;

    @RelatedTo(direction = Direction.OUTGOING, type = "DECLARES", enforceTargetType = true)
    private Set<MethodNode> methods;

    @RelatedTo(direction = Direction.OUTGOING, type = "EXTENDS", enforceTargetType = true)
    private TypeNode superclass;

    @RelatedTo(direction = Direction.OUTGOING, type = "IMPLEMENTS", enforceTargetType = true)
    private Set<TypeNode> interfaces;

    TypeNode() {
    }

    public TypeNode(String name) {
        this(name, null);
    }

    public TypeNode(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.qualifiedName = packageName != null ? packageName + "." + name : name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addMethod(MethodNode method) {
        getMethods().add(method);
    }

    public Set<MethodNode> getMethods() {
        if (methods == null) {
            methods = new HashSet<>();
        }
        return methods;
    }

    public void addField(FieldNode field) {
        getFields().add(field);
    }

    public Set<FieldNode> getFields() {
        if (fields == null) {
            fields = new HashSet<>();
        }
        return fields;
    }

    public void addInterface(TypeNode interface_) {
        getInterfaces().add(interface_);
    }

    public Set<TypeNode> getInterfaces() {
        if (interfaces == null) {
            interfaces = new HashSet<>();
        }
        return interfaces;
    }

    public void setSuperclass(TypeNode superclass) {
        this.superclass = superclass;
    }

    public TypeNode getSuperclass() {
        return superclass;
    }
}
