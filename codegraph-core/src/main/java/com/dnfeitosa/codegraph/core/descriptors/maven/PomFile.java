package com.dnfeitosa.codegraph.core.descriptors.maven;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.codegraph.core.xml.XmlFile;
import com.dnfeitosa.coollections.Function;
import org.jdom2.Element;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;

public class PomFile {

    private final XmlFile xml;

    public PomFile(String path) {
        xml = new XmlFile(new File(path));
    }

    public String getModuleName() {
        return xml.findElement("/ns:project/ns:artifactId").getText();
    }

    public List<Jar> getDependencies() {
        return $(xml.findElements("/ns:project/ns:dependencies/ns:dependency")).map(toJar());
    }

    private Function<Element, Jar> toJar() {
        return dep -> {
            String organization = dep.getChildText("groupId", xml.namespace());
            String name = dep.getChildText("artifactId", xml.namespace());
            String version = dep.getChildText("version", xml.namespace());

            return new Jar(organization, name, version);
        };
    }

    public Set<ArtifactType> getExportTypes() {
        Set<ArtifactType> exportTypes = new HashSet<>();
        exportTypes.add(new ArtifactType(xml.findElement("/ns:project/ns:packaging").getText()));
        return exportTypes;
    }
}
