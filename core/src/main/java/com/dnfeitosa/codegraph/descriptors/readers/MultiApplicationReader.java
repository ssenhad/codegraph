package com.dnfeitosa.codegraph.descriptors.readers;

import com.dnfeitosa.codegraph.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.descriptors.DescriptorType;

import java.io.File;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class MultiApplicationReader implements ApplicationReader {

    private MultiModuleApplicationReader multiModuleApplicationReader;

    public MultiApplicationReader(MultiModuleApplicationReader multiModuleApplicationReader) {
        this.multiModuleApplicationReader = multiModuleApplicationReader;
    }

    @Override
    public Set<ApplicationDescriptor> readAt(String location, DescriptorType descriptorType) throws ReadException {
        return asList(new File(location).listFiles())
                .stream()
                .map(loadDescriptors(descriptorType))
                .filter(desc -> desc != null)
                .flatMap(a -> a.stream())
                .collect(Collectors.toSet());
    }

    private Function<File, Set<ApplicationDescriptor>> loadDescriptors(DescriptorType descriptorType) {
        return appDir -> {
            try {
                return multiModuleApplicationReader.readAt(appDir.getPath(), descriptorType);
            } catch (ReadException e) {
                return null;
            }
        };
    }
}
