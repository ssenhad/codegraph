package com.dnfeitosa.codegraph;

import com.dnfeitosa.codegraph.descriptors.ApplicationDescriptor;
import com.dnfeitosa.codegraph.filesystem.Path;
import com.dnfeitosa.codegraph.model.ArtifactType;
import com.dnfeitosa.codegraph.model.IvyFile;
import com.dnfeitosa.codegraph.model.Jar;
import com.dnfeitosa.codegraph.descriptors.ModuleDescriptor;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class CodeGraphMain {

    public static class IvyModule implements ModuleDescriptor {

        private IvyFile ivyFile;
        private String location;

        public IvyModule(IvyFile ivyFile, String location) {
            this.ivyFile = ivyFile;
            this.location = location;
        }

        @Override
        public String getName() {
            return ivyFile.getModuleName();
        }

        @Override
        public String getLocation() {
            return location;
        }

        @Override
        public List<Jar> getDependencies() {
            return ivyFile.getDependencies();
        }

        @Override
        public Set<ArtifactType> getExportTypes() {
            return ivyFile.getExportTypes();
        }
    }

    public static void main(String[] args) {
        CodeGraphMain codeGraph = new CodeGraphMain();
        codeGraph.execute();
    }

    private void execute() {
        String location = "/Users/dnfeitosa/work/codegraph/test-codebases/src/test/resources/test-codebases/ivy-based-application";
        ApplicationDescriptor applicationDescriptor = new ApplicationDescriptor() {

            @Override
            public String getName() {
                return "ecom";
            }

            @Override
            public String getLocation() {
                return location;
            }

            @Override
            public List<ModuleDescriptor> getModules() {
                String module1Location = Path.join(location, "module1");
                String module2Location = Path.join(location, "module2");
                return asList(
                        new IvyModule(new IvyFile(Path.join(module1Location, "ivy.xml")), module1Location),
                        new IvyModule(new IvyFile(Path.join(module2Location, "ivy.xml")), module2Location)
                );
            }
        };
    }
}
