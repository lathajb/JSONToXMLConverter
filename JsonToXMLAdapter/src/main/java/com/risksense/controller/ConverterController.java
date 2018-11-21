package com.risksense.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	     
		@Autowired
		JSONXMLConverter jxc;
	   
		/**
		 * jsonXMLConverter method is used to convert the given into to corresponding xml tags
		 * @param jsonPayload
		 * @return json to xml converted string
		 * @throws Exception
		 */
	    @PostMapping(path = "/xmlconverter" ,consumes = {"application/json","application/xml"}, produces = {"application/xml","application/json"})
	    public String jsonXMLConverter(@RequestBody String jsonPayload) throws Exception{
	    	String xmlResponse = jxc.convertJSONtoXML(jsonPayload);
	        return  xmlResponse;
	    }

}