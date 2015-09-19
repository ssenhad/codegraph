package com.dnfeitosa.codegraph.core.descriptors.readers;

import com.dnfeitosa.codegraph.core.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;
import com.dnfeitosa.codegraph.core.descriptors.ModuleDescriptor;
import com.dnfeitosa.codegraph.core.descriptors.impl.SimpleApplication;
import com.dnfeitosa.codegraph.core.filesystem.Path;
import com.dnfeitosa.coollections.Filter;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.asSet;
import static java.util.Arrays.asList;

@Component
public class SingleModuleApplicationReader implements ApplicationReader {

    @Override
    public Set<ApplicationDescriptor> readAt(String location, DescriptorType descriptorType, Filter<String> ignores)
            throws ReadException {
        try {
            String descriptorFile = Path.join(location, descriptorType.getFileName());

            ModuleDescriptor moduleDescriptor = descriptorType.buildDescriptor(descriptorFile);
            return asSet(new SimpleApplication(moduleDescriptor.getName(), location, asList(moduleDescriptor)));
        } catch (Exception e) {
            throw new ReadException("Unable to read application descriptors. Is this a single module application?", e);
        }

//        String projectDescriptor = Path.join(location, descriptorType.getFileName());
//
//        List<String> descriptorFiles = fileFinder.find(location, descriptorType.getFileName());
//        List<ModuleDescriptor> moduleDescriptors =
//                $(descriptorFiles)
//                    .filter(descriptorFile -> !descriptorFile.equals(projectDescriptor))
//                    .map(descriptorType::buildDescriptor);
//
//        return asSet(new SimpleApplication(new File(location).getName(), location, moduleDescriptors));
    }


}
