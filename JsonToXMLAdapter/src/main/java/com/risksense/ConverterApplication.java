package com.risksense;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.risksense.converters.JSONXMLConverter;
import com.risksense.factory.ConverterFactory;

/**
 * ConverterApplication - Spring boot application which is used for json to xml convertion
 * @author janlatha
 *
 */
@SpringBootApplication
@Configuration
public class ConverterApplication {

	Logger logger = LoggerFactory.getLogger(ConverterApplication.class);
	
	
	@Bean
	public JSONXMLConverter jSONXMLConverter() throws IOException{
		logger.info("JSONXMLConverter - jSONXMLConverter method for Instance creation");;
		return ConverterFactory.createJSONToXMLConverter();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(ConverterApplication.class, args);
	}
	
}
		