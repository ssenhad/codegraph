package com.dnfeitosa.codegraph.indexing;

import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;
import com.dnfeitosa.codegraph.core.descriptors.readers.ApplicationReader;
import com.dnfeitosa.codegraph.core.descriptors.readers.ReadException;
import com.dnfeitosa.codegraph.core.loaders.ApplicationLoader;
import com.dnfeitosa.codegraph.core.model.Application;
import com.dnfeitosa.codegraph.services.ApplicationService;
import com.dnfeitosa.coollections.Filter;

import java.util.Set;

public class ApplicationIndexer {

    private final ApplicationReader applicationReader;
    private final ApplicationLoader applicationLoader;
    private final ApplicationService applicationService;

    public ApplicationIndexer(ApplicationReader applicationReader, ApplicationLoader applicationLoader,
        ApplicationService applicationService) {
        this.applicationReader = applicationReader;
        this.applicationLoader = applicationLoader;
        this.applicationService = applicationService;
    }

    public void index(String codebasePath, DescriptorType descriptorType, Filter<String> ignores) {
        try {
            Set<ApplicationDescriptor> descriptors = applicationReader.readAt(codebasePath, descriptorType, ignores);
            descriptors.parallelStream().forEach(descriptor -> {
                Application application = applicationLoader.load(descriptor);
                applicationService.save(application);
            });
        } catch (ReadException e) {
            e.printStackTrace();
        }
    }
}