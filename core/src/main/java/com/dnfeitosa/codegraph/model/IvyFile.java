package com.dnfeitosa.codegraph.model;

import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.codegraph.xml.XmlFile;
import org.jdom2.Element;

import java.io.File;
import java.util.List;
import java.util.Set;

import static com.dnfeitosa.coollections.Coollections.$;

public class IvyFile {

	private XmlFile xml;
	private File file;

	public IvyFile(String path) {
		this.file = new File(path);
		this.xml = new XmlFile(file);
	}

	public String getModuleName() {
		return xml.findAttribute("/ivy-module/info/@module").getValue();
	}

	public String getLocation() {
		return file.getParent();
	}

	public List<Jar> getDependencies() {
		return $(xml.findElements("/ivy-module/dependencies/dependency")).map(elementToJar());
	}

	private Function<Element, Jar> elementToJar() {
		return new Function<Element, Jar>() {
			public Jar apply(Element element) {
				String organization = element.getAttribute("org").getValue();
				String name = element.getAttribute("name").getValue();
				String version = element.getAttribute("rev").getValue();
				return new Jar(organization, name, version);
			}
		};
	}

	public Set<ArtifactType> getExportTypes() {
		return $(xml.findElements("/ivy-module/publications/artifact")).map(toArtifactType()).toSet();
	}

	private Function<Element, ArtifactType> toArtifactType() {
		return new Function<Element, ArtifactType>() {
			public ArtifactType apply(Element element) {
				String type = element.getAttribute("type").getValue();
				return ArtifactType.fromName(type.toUpperCase());
			}
		};
	}
}
