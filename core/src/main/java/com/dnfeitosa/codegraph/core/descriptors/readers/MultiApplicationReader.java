package com.dnfeitosa.codegraph.core.descriptors.readers;

import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;
import com.dnfeitosa.coollections.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Component
public class MultiApplicationReader implements ApplicationReader {

    private MultiModuleApplicationReader multiModuleApplicationReader;

    @Autowired
    public MultiApplicationReader(MultiModuleApplicationReader multiModuleApplicationReader) {
        this.multiModuleApplicationReader = multiModuleApplicationReader;
    }

    @Override
    public Set<ApplicationDescriptor> readAt(String location, DescriptorType descriptorType, Filter<String> ignores) throws ReadException {
        return getApplicationsInsideOf(location)
                .filter(file -> !ignores.matches(file.getName()))
                .map(appDir -> loadDescriptor(descriptorType, appDir))
                .filter(descriptor -> descriptor != null)
                .flatMap(a -> a.stream())
                .collect(Collectors.toSet());
    }

    private Stream<File> getApplicationsInsideOf(String applicationsLocation) {
        return asList(new File(applicationsLocation).listFiles()).stream();
    }

    private Set<ApplicationDescriptor> loadDescriptor(DescriptorType descriptorType, File appDir) {
        try {
            return multiModuleApplicationReader.readAt(appDir.getPath(), descriptorType, x -> false);
        } catch (ReadException e) {
            return null;
        }
    }
}
