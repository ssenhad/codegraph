package com.dnfeitosa.codegraph.core.descriptors.readers;

import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.impl.SimpleApplication;
import com.dnfeitosa.codegraph.core.filesystem.Path;
import com.dnfeitosa.codegraph.core.loaders.finders.FileFinder;
import com.dnfeitosa.coollections.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;
import static com.dnfeitosa.coollections.Coollections.asSet;

@Component
public class MultiModuleApplicationReader implements ApplicationReader {

    private FileFinder fileFinder;

    @Autowired
    public MultiModuleApplicationReader(FileFinder fileFinder) {
        this.fileFinder = fileFinder;
    }

    @Override
    public Set<ApplicationDescriptor> readAt(String location, DescriptorType descriptorType, Filter<String> ignores) throws ReadException {
        String appDescriptorPath = Path.join(location, descriptorType.getFileName());

        List<String> descriptorFiles = findModuleDescriptors(location, descriptorType);
        List<ModuleDescriptor> moduleDescriptors = $(descriptorFiles)
            .filter(filePath -> !filePath.equals(appDescriptorPath))
            .filter(filePath -> !ignores.matches(new File(filePath).getParentFile().getName()))
            .map(filePath -> descriptorType.buildDescriptor(filePath));

        String applicationName = getApplicationNameFrom(location);

        return asSet(new SimpleApplication(applicationName, location, moduleDescriptors));
    }

    private List<String> findModuleDescriptors(String location, DescriptorType descriptorType) {
        return fileFinder.find(location, descriptorType.getFileName());
    }

    private String getApplicationNameFrom(String location) {
        return new File(location).getName();
    }
}
