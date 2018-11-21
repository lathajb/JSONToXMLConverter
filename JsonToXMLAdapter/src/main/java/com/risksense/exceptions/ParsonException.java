package com.risksense.exceptions;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Exception while parsing JSON to XML") //400
public class ParsonException extends Exception {

		private static final long serialVersionUID = -3332292346834265371L;

		public ParsonException(ParserConfigurationException pce){
			super(pce.getCause());
		}
}