package com.risksense.converters;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.risksense.constants.Constants;
import com.risksense.exceptions.ParsonException;

/**
 * JSONXMLConverterImpl is the implementation class which facilitates how to
 * construct xml string by the given json
 * 
 * @author janlatha
 *
 */
public class JSONXMLConverterImpl implements JSONXMLConverter {

	Logger logger = LoggerFactory.getLogger(JSONXMLConverterImpl.class);

	public JSONXMLConverterImpl() {
		logger.info("JSONXMLConverterImpl Object Instatiating");
	}

	/**
	 * convertJSONtoXML - manipulate and construct the xml string response
	 * 
	 * @return XML string
	 * @param json request payload
	 * @throws IOException
	 */
	@Override
	public String convertJSONtoXML(String jsonPayload) throws Exception {

		logger.info("JSONXMLConverterImpl - convertJSONtoXML method Invoked");
		Element rootElement = null;
		DocumentBuilderFactory docFactory = null;
		DocumentBuilder docBuilder = null;
		Document doc = null;
		String responseXML = null;

		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			char c = jsonPayload.charAt(0);
			if (c == '{') {

				JSONObject jsonObj = new JSONObject(jsonPayload);
				Element rootObjectElement = doc.createElement(Constants.JSONOBJECT_ELE);

				for (Object key : jsonObj.keySet()) {
					Element childEle = null;
					String keyStr = (String) key;
					Object keyvalue = jsonObj.get(keyStr);
					childEle = iterateJsonObject(doc, keyvalue, childEle);
					// creating the attribute property
					Attr objAttribute = doc.createAttribute(Constants.ATTR_NAME);
					objAttribute.setValue(keyStr);
					childEle.setAttributeNode(objAttribute);

					rootObjectElement.appendChild(childEle);
				}
				rootElement = rootObjectElement;
			} else if (c == '[') {

				JSONArray jsonArrayElements = new JSONArray(jsonPayload);
				Element rootObjectElement = doc.createElement(Constants.ARRAY_ELE);

				for (Object jsonArrayElem : jsonArrayElements) {
					Element childEle = null;
					childEle = iterateJsonObject(doc, jsonArrayElem, childEle);
					rootObjectElement.appendChild(childEle);
				}
				rootElement = rootObjectElement;

			} else {
				rootElement = processString(doc, jsonPayload, rootElement, Constants.OTHER_TYPES);
			}

			doc.appendChild(rootElement);
			responseXML = convertXmlToString(doc);

		} catch (NumberFormatException nfe) {
			throw new NumberFormatException();
		} catch (ParserConfigurationException pce) {
			throw new ParsonException(pce);
		} catch (TransformerException te) {
			throw new TransformerException(te.getCause());
		}

		logger.info("JSONXMLConverterImpl - convertJSONtoXML method execution completed");
		return responseXML;

	}

	/**
	 * convertXmlToString - which will convert given xml to string format
	 * 
	 * @param document
	 * @return
	 * @throws TransformerException
	 */
	private String convertXmlToString(Document document) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DOMSource source = new DOMSource(document);

		StringWriter strWriter = new StringWriter();
		StreamResult result = new StreamResult(strWriter);
		transformer.transform(source, result);
		return strWriter.getBuffer().toString();
	}

	/**
	 * constructXMLElement - Used to construct basic xml elements
	 * 
	 * @param doc
	 * @param element
	 * @param value
	 * @param isObjectOrArray
	 * @return
	 * @throws TransformerException
	 */
	private Element constructXMLElement(Document doc, String elementName, String value, String isObjectOrArray)
			throws TransformerException {
		Element rootElement = doc.createElement(elementName);
		if (!elementName.equals(Constants.NULL)) {
			if (value != null)
				rootElement.appendChild(doc.createTextNode(value));
		}
		return rootElement;
	}

	/**
	 * isNumeric - To validate given string is numeric or not
	 * 
	 * @param strNum
	 * @return
	 */
	private boolean isNumeric(String strNum) {
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * processString - To process the given string to corresponding types
	 * 
	 * @param doc
	 * @param arrayElem
	 * @param childEle
	 * @param isObjectOrArray
	 * @return
	 * @throws TransformerException
	 */
	private Element processString(Document doc, String arrayElem, Element childEle, String isObjectOrArray)
			throws TransformerException {
		if (isNumeric(arrayElem)) {
			childEle = constructXMLElement(doc, Constants.NUMBER, arrayElem, isObjectOrArray);
		} else if (arrayElem.equalsIgnoreCase(Constants.BOOLEAN_TRUE)
				|| arrayElem.equalsIgnoreCase(Constants.BOOLEAN_FALSE)) {
			childEle = constructXMLElement(doc, Constants.BOOLEAN_ELE, arrayElem, isObjectOrArray);
		} else if (arrayElem.equalsIgnoreCase(Constants.NULL)) {
			childEle = constructXMLElement(doc, Constants.NULL, arrayElem, isObjectOrArray);
		} else {

			if (isObjectOrArray.equals(Constants.JSONOBJECT_ELE)) {
				childEle = constructXMLElement(doc, Constants.JSONOBJECT_ELE, null, isObjectOrArray);
			} else {
				childEle = constructXMLElement(doc, Constants.STRING_ELE, arrayElem, isObjectOrArray);
			}

		}

		return childEle;
	}

	/**
	 * utitlityn - which will checks the json object type and generate corresponds
	 * xml element
	 * 
	 * @param doc
	 * @param keyvalue
	 * @param childEle
	 * @return
	 * @throws TransformerException
	 */
	private Element utitlity(Document doc, Object keyvalue, Element childEle) throws TransformerException {

		try {
			String valueString = null;
			if (keyvalue instanceof Double || keyvalue instanceof Integer) {
				valueString = String.valueOf(keyvalue);
				childEle = constructXMLElement(doc, Constants.NUMBER, valueString, Constants.OTHER_TYPES);
			} else if (keyvalue instanceof Boolean) {
				valueString = String.valueOf(keyvalue);
				childEle = constructXMLElement(doc, Constants.BOOLEAN_ELE, valueString, Constants.OTHER_TYPES);
			} else {
				valueString = String.valueOf(keyvalue);
				childEle = processString(doc, valueString, childEle, Constants.OTHER_TYPES);
			}
		} catch (TransformerException te) {
			throw new TransformerException(te.getCause());
		}
		return childEle;
	}

	/**
	 * processArray - To process given array
	 * 
	 * @param doc
	 * @param keyvalue
	 * @param childEle
	 * @return
	 * @throws TransformerException
	 */
	private Element processArray(Document doc, Object keyvalue, Element childEle) throws TransformerException {

		Element arrayRootElement = doc.createElement(Constants.ARRAY_ELE);

		JSONArray arrayElements = (JSONArray) keyvalue;

		for (Object jsonArrayItem : arrayElements) {
			if (jsonArrayItem instanceof JSONArray) {
				childEle = processArray(doc, jsonArrayItem, childEle);
			} else if (jsonArrayItem instanceof JSONObject) {
				String jsonObj = String.valueOf(jsonArrayItem);
				childEle = processJsonObject(jsonObj, doc);
			} else {
				childEle = utitlity(doc, jsonArrayItem, arrayRootElement);
			}
			arrayRootElement.appendChild(childEle);
		}

		return arrayRootElement;
	}

	/**
	 * processJsonObject - To process JSON Object
	 * 
	 * @param jsonPayload
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	private Element processJsonObject(String jsonPayload, Document doc) throws TransformerException {

		JSONObject jsonObj = new JSONObject(jsonPayload);
		Element rootObjectElement = doc.createElement(Constants.JSONOBJECT_ELE);

		for (Object key : jsonObj.keySet()) {

			Element childEle = null;
			// based on you key types
			String keyStr = (String) key;
			Object keyvalue = jsonObj.get(keyStr);

			if (keyvalue instanceof JSONObject) {
				// childEle = processString(doc, Constants.JSONOBJECT_ELE, childEle,
				// Constants.JSONOBJECT_ELE);
				String complextJsonObj = String.valueOf(keyvalue);
				childEle = processJsonObject(complextJsonObj, doc);
			} else if (keyvalue instanceof JSONArray) {
				childEle = processArray(doc, keyvalue, childEle);
			} else {
				childEle = utitlity(doc, keyvalue, childEle);
			}

			Attr objAttribute = doc.createAttribute(Constants.ATTR_NAME);
			objAttribute.setValue(keyStr);
			childEle.setAttributeNode(objAttribute);

			rootObjectElement.appendChild(childEle);
		}
		return rootObjectElement;
	}

	/**
	 * iterateJsonObject - To traverse over the JSON Object and create an Element
	 * 
	 * @param doc
	 * @param keyvalue
	 * @param childEle
	 * @return
	 * @throws TransformerException
	 */
	private Element iterateJsonObject(Document doc, Object keyvalue, Element childEle) throws TransformerException {

		if (keyvalue instanceof JSONObject) {
			String strJsonObj = String.valueOf(keyvalue);
			childEle = processJsonObject(strJsonObj, doc);
		} else if (keyvalue instanceof JSONArray) {
			childEle = processArray(doc, keyvalue, childEle);
		} else {
			childEle = utitlity(doc, keyvalue, childEle);
		}
		return childEle;
	}

}
