package com.risksense.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants
 * @author janlatha
 *
 */
public class Constants {
	
	Logger logger = LoggerFactory.getLogger(Constants.class);
	
	private Constants() {
		logger.info("Inside Constants - constructer");
	}
	
	public static final String NUMBER = "number";	
	public static final String BOOLEAN_TRUE = "true";
	public static final String BOOLEAN_FALSE = "false";
	public static final String NULL = "null";
	public static final String JSONOBJECT_ELE = "object";
	public static final String ARRAY_ELE = "array";
	public static final String ATTR_NAME = "name";
	public static final String OTHER_TYPES = "others";
	public static final String BOOLEAN_ELE ="boolean";
	public static final String STRING_ELE = "string";
	
}
