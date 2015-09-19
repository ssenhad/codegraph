package com.dnfeitosa.codegraph.core.descriptors.ivy;

import com.dnfeitosa.codegraph.core.model.ArtifactType;
import com.dnfeitosa.codegraph.core.model.Jar;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.core.xml.XmlFile;
import org.jdom2.Element;

import java.io.File;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;

public class IvyFile {

    private final String location;
    private final XmlFile xml;

	public IvyFile(String path) {
        File file = new File(path);
		this.xml = new XmlFile(file);
        this.location = file.getParent();
	}

    public String getModuleName() {
		return xml.findAttribute("/ivy-module/info/@module").getValue();
	}

    public String getLocation() {
		return location;
	}

    public List<Jar> getDependencies() {
		return $(xml.findElements("/ivy-module/dependencies/dependency")).map(elementToJar());
	}

	private Function<Element, Jar> elementToJar() {
		return element -> {
            String organization = element.getAttribute("org").getValue();
            String name = element.getAttribute("name").getValue();
            String version = element.getAttribute("rev").getValue();
            return new Jar(organization, name, version);
        };
	}

	public Set<ArtifactType> getExportTypes() {
		return $(xml.findElements("/ivy-module/publications/artifact")).map(toArtifactType()).toSet();
	}

	private Function<Element, ArtifactType> toArtifactType() {
		return element -> {
            String type = element.getAttribute("type").getValue();
            return new ArtifactType(type);
        };
	}
}
