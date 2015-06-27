package com.dnfeitosa.codegraph.xml;

import static org.jdom2.filter.Filters.attribute;
import static org.jdom2.filter.Filters.element;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class XmlFile {

	private final Document document;

	public XmlFile(File file) {
		document = buildDocumentFrom(file);
	}

	public Attribute findAttribute(String expression) {
		return compile(attribute(), expression).evaluateFirst(document);
	}

	public List<Element> findElements(String expression) {
		return compile(element(), expression).evaluate(document);
	}

	private <T> XPathExpression<T> compile(Filter<T> filter, String expression) {
		return XPathFactory.instance().compile(expression, filter);
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