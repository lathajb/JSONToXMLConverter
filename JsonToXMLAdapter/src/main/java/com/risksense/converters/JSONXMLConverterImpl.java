package com.risksense.converters;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JSONXMLConverterImpl implements JSONXMLConverter{

	@Override
	public String convertJSONtoXML(String jsonPayload) throws IOException {
		
		String xmlResponse = null;
		
		try {
			 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			 // root elements
			 Document doc = docBuilder.newDocument();
			 xmlResponse = constructXMLElement(doc,"string",jsonPayload);
		}catch(Exception e) {
			
		}
		return xmlResponse;
		
	}
	
	private static String toXmlString(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter strWriter = new StringWriter();
        StreamResult result = new StreamResult(strWriter);
        transformer.transform(source, result);
        return strWriter.getBuffer().toString(); 
    }
	
	
	private static String constructXMLElement(Document doc,String element, String value) throws TransformerException {
		 Element rootElement = doc.createElement(element);
		 rootElement.appendChild(doc.createTextNode(value));
		 doc.appendChild(rootElement);
		 return toXmlString(doc);
	}
	
}
