package com.dnfeitosa.codegraph.descriptors.detectors;

import com.dnfeitosa.codegraph.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.descriptors.DescriptorTypes;
import com.dnfeitosa.codegraph.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.descriptors.impl.SimpleApplication;
import com.dnfeitosa.codegraph.loaders.finders.FileFinder;

import java.io.File;
import java.util.List;

import static com.dnfeitosa.coollections.Coollections.$;

public class ApplicationDetector {

    private FileFinder fileFinder;

    public ApplicationDetector(FileFinder fileFinder) {
        this.fileFinder = fileFinder;
    }

    public ApplicationDescriptor detectAt(String location, DescriptorTypes descriptorType) {
        List<String> descriptorFiles = fileFinder.find(location, descriptorType.getFileName());
        List<ModuleDescriptor> moduleDescriptors = $(descriptorFiles)
            .map(descriptorType::buildDescriptor);

        return new SimpleApplication(new File(location).getName(), location, moduleDescriptors);
    }
}
