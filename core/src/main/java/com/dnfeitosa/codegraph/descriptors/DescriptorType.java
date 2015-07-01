package com.dnfeitosa.codegraph.descriptors;

import com.dnfeitosa.codegraph.descriptors.ivy.IvyFile;
import com.dnfeitosa.codegraph.descriptors.ivy.IvyModuleDescriptor;
import com.dnfeitosa.codegraph.descriptors.maven.MavenModuleDescriptor;
import com.dnfeitosa.codegraph.descriptors.maven.PomFile;

public enum DescriptorType {

    IVY("ivy.xml") {
        @Override
        public ModuleDescriptor buildDescriptor(String file) {
            return new IvyModuleDescriptor(new IvyFile(file));
        }

    }, MAVEN("pom.xml") {

        @Override
        public ModuleDescriptor buildDescriptor(String file) {
            return new MavenModuleDescriptor(new PomFile(file));
        }
    };

    private String fileName;

    DescriptorType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public abstract ModuleDescriptor buildDescriptor(String file);
}
