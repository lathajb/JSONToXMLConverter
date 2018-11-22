package com.risksense.factory;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.risksense.converters.JSONXMLConverter;
import com.risksense.converters.JSONXMLConverterImpl;

/**
 * Factory class for creating instances of {@link JSONXMLConverter}.
 */
public final class ConverterFactory {

	Logger logger = LoggerFactory.getLogger(ConverterFactory.class);
	
	private static JSONXMLConverter jsonxmlConverter;
	
	private ConverterFactory() {
		logger.info("ConverterFactory - constructor Invoked...");
	}

	/**
	 * You should implement this method having it return your version of
	 * {@link com.risksense.converters.JSONXMLConverter}.
	 *
	 * @return {@link com.risksense.converters.JSONXMLConverter} implementation you
	 *         created.
	 * @throws IOException
	 */
	public static final synchronized JSONXMLConverter createJSONToXMLConverter() {
		if (jsonxmlConverter == null) {
			jsonxmlConverter = new JSONXMLConverterImpl();
		}
		return jsonxmlConverter;

	}
}
