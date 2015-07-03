package com.dnfeitosa.codegraph.indexing;

import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;
import com.dnfeitosa.codegraph.core.descriptors.readers.ApplicationReader;
import com.dnfeitosa.codegraph.services.ApplicationService;

public class ApplicationIndexer {

    private ApplicationReader applicationReader;
    private final ApplicationService applicationService;

    public ApplicationIndexer(ApplicationReader applicationReader, ApplicationService applicationService) {
        this.applicationReader = applicationReader;
        this.applicationService = applicationService;
    }

    public void index(String codebasePath, DescriptorType descriptorType) {
    }
}