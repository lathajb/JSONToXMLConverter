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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class JSONXMLConverterImpl implements JSONXMLConverter {

	@Override
	public String convertJSONtoXML(String jsonPayload) throws IOException{
		
		
		
		Element rootElement = null;
		DocumentBuilderFactory docFactory = null;
		DocumentBuilder docBuilder = null;
		Document doc = null;
		String responseXML = null;
		
		try {
			
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			int arrayIndex = jsonPayload.indexOf("[");
			
			if(arrayIndex == -1 ? false : true ) {
				rootElement = arrayProcessing(doc,jsonPayload);
			}else {
				rootElement = processString(doc,jsonPayload,rootElement);
			}
				
			doc.appendChild(rootElement);
			responseXML = toXmlString(doc);
			
		}catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(TransformerException te) {
			te.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseXML;
		
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
	
	
	private Element constructXMLElement(Document doc,String element, String value) throws TransformerException {
		 Element rootElement = doc.createElement(element);
		 if(!element.equals("null")) {
			if(value != null)
			rootElement.appendChild(doc.createTextNode(value));
		 }
		 return rootElement;
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
	
	
	private Element arrayProcessing(Document doc ,String jsonPayload) throws TransformerException, JSONException {
		
		String [] arrayCommaSliper = jsonPayload.replaceFirst("[\\[]", "").replaceFirst("[\\]]", "").split(",");
		Element rootElement = doc.createElement("array");
		
		for(String arrayElem : arrayCommaSliper) {
			
			Element childEle = null;
			
			int childArray = arrayElem.indexOf("[");
			
			if(childArray == -1 ? false : true) {
				arrayElem.replaceFirst("\\[", "");
				arrayProcessing(doc,arrayElem);
			}
			childEle = processString(doc,arrayElem,childEle);
		 	rootElement.appendChild(childEle);
			
			//doc.appendChild(rootElement);
		}
		return rootElement;
	}
	
	
	private Element processString(Document doc,String arrayElem,Element childEle) throws TransformerException {
		if(isNumeric(arrayElem)) {	
			childEle =  constructXMLElement(doc,"number",arrayElem);
		}else if(arrayElem.equals("true") || arrayElem.equals("false") || arrayElem.equals("TRUE") || arrayElem.equals("FALSE") ) {
			childEle =  constructXMLElement(doc,"boolean",arrayElem);
		}else if(arrayElem.equals("null") || arrayElem.equals("NULL")) {
			childEle =  constructXMLElement(doc,"null",arrayElem);
		}else {
			childEle =  constructXMLElement(doc,"string",arrayElem);
		}
		return childEle;
	}
	

	
}
