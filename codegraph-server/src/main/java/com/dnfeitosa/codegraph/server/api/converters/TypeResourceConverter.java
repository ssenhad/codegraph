package com.dnfeitosa.codegraph.server.api.converters;

import com.dnfeitosa.codegraph.core.models.Type;
import com.dnfeitosa.codegraph.server.api.resources.ArtifactResource;
import com.dnfeitosa.codegraph.server.api.resources.TypeResource;
import com.dnfeitosa.codegraph.server.api.resources.TypesResource;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TypeResourceConverter {

    public TypesResource toResource(ArtifactResource artifact, Set<Type> types) {
        TypesResource typesResource = new TypesResource(artifact);
        types.forEach(t -> typesResource.add(toResource(t)));
        return typesResource;
    }

    public TypeResource toResource(Type type) {
        TypeResource resource = new TypeResource();
        resource.setType(type.getType());
        resource.setName(type.getName());
        resource.setPackageName(type.getPackageName());
        resource.setUsage(type.getUsage());
        return resource;
    }

    public Type toModel(TypeResource type) {
        return new Type(type.getName(), type.getPackageName(), type.getUsage(), type.getType());
    }
}
