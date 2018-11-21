package com.risksense.converters;

import java.io.IOException;

/**
 * This interface provides methods that are required for creating a converter from JSON to XML.
 */
public interface JSONXMLConverter{

    /**
     * Reads in the JSON from the given request body and outputs the data, converted to
     * XML, to the client. Exceptions are thrown by this method so that the
     * caller can clean up the before exiting.
     *
     * @param json {@link java.io.File} from which to read JSON data.
     *
     * @throws java.io.IOException
     */
    public String convertJSONtoXML(String json) throws IOException;
}
