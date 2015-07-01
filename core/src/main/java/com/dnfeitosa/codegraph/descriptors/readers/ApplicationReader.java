package com.dnfeitosa.codegraph.descriptors.readers;

import com.dnfeitosa.codegraph.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.descriptors.DescriptorType;

import java.util.Set;

public interface ApplicationReader {
    Set<ApplicationDescriptor> readAt(String location, DescriptorType descriptorType) throws ReadException;
}
