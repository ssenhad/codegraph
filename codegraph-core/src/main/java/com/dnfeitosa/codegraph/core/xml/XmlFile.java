package com.dnfeitosa.codegraph.core.xml;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.jdom2.filter.Filters.attribute;
import static org.jdom2.filter.Filters.element;

public class XmlFile {

	private final Document document;
    private String defaultNamespacePrefix;

    public XmlFile(File file) {
        this(file, "");
    }

    public XmlFile(File file, String defaultNamespacePrefix) {
        this.defaultNamespacePrefix = defaultNamespacePrefix;
        this.document = buildDocumentFrom(file);
    }

	public Attribute findAttribute(String expression) {
		return compile(attribute(), expression).evaluateFirst(document);
	}

	public List<Element> findElements(String expression) {
		return compile(element(), expression).evaluate(document);

    }

    public Element findElement(String expression) {
		return compile(element(), expression).evaluateFirst(document);
	}

	private <T> XPathExpression<T> compile(Filter<T> filter, String expression) {
        XPathFactory instance = XPathFactory.instance();
        return instance.compile(expression, filter, null, namespace());
	}

    /**
     * This is necessary due to the way JDOM handles default namespaces: XPath expressions cannot find elements
     * belonging to such namespaces and requires it to be reassigned to a custom prefix.
     *
     * @return Collection with a single element representing the default namespace mapped to 'ns' preffix.
     */
    public Namespace namespace() {
        Namespace namespace = document.getRootElement().getNamespace("");
        if (!namespace.getURI().isEmpty()) {
            return Namespace.getNamespace(defaultNamespacePrefix, namespace.getURI());
        }
        return document.getRootElement().getNamespace();
    }

    private Document buildDocumentFrom(File file) {
		try {
			SAXBuilder builder = new SAXBuilder(XMLReaders.NONVALIDATING);
			return builder.build(file);
		} catch (JDOMException e) {
			throw new XmlFileException(e);
		} catch (IOException e) {
			throw new XmlFileException(e);
		}
	}
}