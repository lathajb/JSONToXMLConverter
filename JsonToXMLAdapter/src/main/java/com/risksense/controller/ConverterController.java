package com.risksense.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.risksense.converters.JSONXMLConverter;

/**
 * ConverterController
 * @author janlatha
 *
 */
@RestController
public class ConverterController {

		Logger logger = LoggerFactory.getLogger(ConverterController.class);
		
		@Autowired
		JSONXMLConverter jxc;
	   
		/**
		 * jsonXMLConverter method is used to convert the given into to corresponding xml tags
		 * @param jsonPayload
		 * @return json to xml converted string
		 * @throws Exception
		 */
	    @PostMapping(path = "/xmlconverter" ,consumes = {"application/json","application/xml"}, produces = {"application/xml","application/json"})
	    public ResponseEntity<String> jsonXMLConverter(@RequestBody String jsonPayload) throws Exception{
	    	logger.info("ConverterController Invoked");
	    	String xmlResponse = jxc.convertJSONtoXML(jsonPayload);
	    	logger.info("ConverterControllerjsonXMLConverter method execution Completed");
	    	return new ResponseEntity<String>(xmlResponse, HttpStatus.OK);
	    }

}