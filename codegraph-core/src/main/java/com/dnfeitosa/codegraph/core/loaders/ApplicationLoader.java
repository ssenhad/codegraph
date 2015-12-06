package com.dnfeitosa.codegraph.core.loaders;

import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.core.model.Module;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

@Component
public class ApplicationLoader {

	public Application load(ApplicationDescriptor applicationDescriptor) {
        List<Module> modules = $(applicationDescriptor.getModules())
            .map(this::toModule);

        return new Application(applicationDescriptor.getName(), modules);
    }

    private Module toModule(ModuleDescriptor descriptor) {
        return new Module(descriptor.getName(), descriptor.getOrganization(), descriptor.getDependencies(), descriptor.getExportTypes());
    }
}
