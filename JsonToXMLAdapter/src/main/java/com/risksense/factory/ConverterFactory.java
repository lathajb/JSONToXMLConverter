package com.risksense.factory;

import java.io.IOException;

import com.risksense.converters.JSONXMLConverter;
import com.risksense.converters.JSONXMLConverterImpl;

/**
 * Factory class for creating instances of {@link JSONXMLConverter}.
 */
public final class ConverterFactory {

	private static JSONXMLConverter jsonxmlConverter;

	/**
	 * You should implement this method having it return your version of
	 * {@link com.risksense.converters.JSONXMLConverter}.
	 *
	 * @return {@link com.risksense.converters.JSONXMLConverter} implementation you
	 *         created.
	 * @throws IOException
	 */
	public static final JSONXMLConverter createJSONToXMLConverter() throws IOException {
		if (jsonxmlConverter == null) {
			jsonxmlConverter = new JSONXMLConverterImpl();
		}
		return jsonxmlConverter;

	}
}
