package com.risksense.converters;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.assertj.core.util.Arrays;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JSONXMLConverterImpl implements JSONXMLConverter {

	@Override
	public String convertJSONtoXML(String jsonPayload) throws IOException{
		
		String xmlResponse = null;
		DocumentBuilderFactory docFactory = null;
		DocumentBuilder docBuilder = null;
		Document doc = null;
		
		try {
			
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			int arrayIndex = jsonPayload.indexOf("[");
			
			if(isNumeric(jsonPayload)) {
				 xmlResponse = constructXMLElement(doc,"number",jsonPayload);
			}else if(jsonPayload.equals("true") || jsonPayload.equals("false") || jsonPayload.equals("TRUE") || jsonPayload.equals("FALSE") ) {
				xmlResponse = constructXMLElement(doc,"boolean",jsonPayload);
			}else if(jsonPayload.equals("null") || jsonPayload.equals("NULL")) {
				xmlResponse = constructXMLElement(doc,"null",jsonPayload);
			
			}else if(arrayIndex == -1 ? false : true ) {
				
					String [] arrayCommaSliper = jsonPayload.replaceAll("[\\[\\]]", "").split(",");
					
					for(String arrayElem : arrayCommaSliper) {
						if(isNumeric(arrayElem)) {
							 xmlResponse = constructXMLElement(doc,"number",arrayElem);
						}else if(arrayElem.equals("true") || arrayElem.equals("false") || arrayElem.equals("TRUE") || arrayElem.equals("FALSE") ) {
							xmlResponse = constructXMLElement(doc,"boolean",arrayElem);
						}else if(arrayElem.equals("null") || arrayElem.equals("NULL")) {
							xmlResponse = constructXMLElement(doc,"null",arrayElem);
						}else {
							xmlResponse = constructXMLElement(doc,"string",arrayElem);
						}
						
					}
					
					//xmlResponse = constructXMLElement(doc,"string",jsonPayload);
			}else {
				xmlResponse = constructXMLElement(doc,"string",jsonPayload);
			}
				
			 
		}catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(TransformerException te) {
			te.printStackTrace();
		}
		return xmlResponse;
		
	}
	
	private String toXmlString(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter strWriter = new StringWriter();
        StreamResult result = new StreamResult(strWriter);
        transformer.transform(source, result);
        return strWriter.getBuffer().toString(); 
    }
	
	
	private String constructXMLElement(Document doc,String element, String value) throws TransformerException {
		 Element rootElement = doc.createElement(element);
		 
		 if(!element.equals("null")) {
			rootElement.appendChild(doc.createTextNode(value));
		 }
		 
		 doc.appendChild(rootElement);
		 
		 return toXmlString(doc);
	}
	
	
	private boolean isNumeric(String strNum) {
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
	private boolean isArray(String jsonArray) {
		Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
		Pattern special = Pattern.compile ("\\[\\]");
		Matcher hasLetter = letter.matcher(jsonArray);
        Matcher hasDigit = digit.matcher(jsonArray);
		Matcher hasSpecial = special.matcher(jsonArray);
		return (hasLetter.find() || hasDigit.find())&& hasSpecial.find();
	}
	

	
}
