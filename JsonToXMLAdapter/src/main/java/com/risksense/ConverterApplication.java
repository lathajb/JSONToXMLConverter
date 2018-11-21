package com.risksense;

import java.io.IOException;

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

	@Bean
	public JSONXMLConverter jSONXMLConverter() throws IOException{
		return ConverterFactory.createJSONToXMLConverter();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(ConverterApplication.class, args);
	}
	
}
		