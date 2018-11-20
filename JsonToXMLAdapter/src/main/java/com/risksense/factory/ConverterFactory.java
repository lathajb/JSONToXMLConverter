package com.risksense.factory;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.risksense.converters.JSONXMLConverter;
import com.risksense.converters.JSONXMLConverterImpl;

/**
 * Factory class for creating instances of {@link JSONXMLConverter}.
 */
public final class ConverterFactory {

	private final  JSONXMLConverter jsonxmlConverter;
	
	private ConverterFactory(JSONXMLConverter jsonxmlConverter) {
		this.jsonxmlConverter = jsonxmlConverter;
	}
	
	
	
    /**
     * You should implement this method having it return your version of
     * {@link com.risksense.converters.JSONXMLConverter}.
     *
     * @return {@link com.risksense.converters.JSONXMLConverter} implementation you created.
     * @throws IOException 
     */
    public static final JSONXMLConverter createJSONToXMLConverter() throws IOException {
    	JSONXMLConverter jsonxmlConverter = new JSONXMLConverterImpl();
    return jsonxmlConverter;
      
    }
}
