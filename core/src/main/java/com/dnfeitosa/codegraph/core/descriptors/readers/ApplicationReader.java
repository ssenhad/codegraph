package com.dnfeitosa.codegraph.core.descriptors.readers;

import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;

import java.util.Set;

public interface ApplicationReader {
    Set<ApplicationDescriptor> readAt(String location, DescriptorType descriptorType) throws ReadException;
}
