package com.dnfeitosa.codegraph.loaders;

import com.dnfeitosa.codegraph.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.model.Application;
import com.dnfeitosa.codegraph.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

@Component
public class ApplicationLoader {

    @Autowired
	public ApplicationLoader() {
	}

	public Application load(ApplicationDescriptor applicationDescriptor) {
        List<Module> modules = $(applicationDescriptor.getModules())
            .map(this::toModule);

        return new Application(applicationDescriptor.getName(), modules);
    }

    private Module toModule(ModuleDescriptor mod) {
        return new Module(mod.getName(), null, mod.getDependencies(), mod.getExportTypes());
    }
}
