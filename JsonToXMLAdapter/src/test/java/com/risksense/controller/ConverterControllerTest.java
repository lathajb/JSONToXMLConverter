package com.risksense.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(ConverterController.class)
public class ConverterControllerTest {
   
   
		@Autowired
		private MockMvc mockMvc;
	
			
		@Test
		public void testForArrayObj() throws Exception {
		
				String content = "[1,[],[1, \"red\"]]";
				String responsePayload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><array><number>1</number><array/><array><number>1</number><string>red</string></array></array>";
				RequestBuilder requestBuilder = MockMvcRequestBuilders
						.post("/xmlconverter")
						.accept(MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML).content(content)
						.contentType(MediaType.APPLICATION_XML);

				MvcResult result = mockMvc.perform(requestBuilder).andReturn();
				MockHttpServletResponse response = result.getResponse();
				assertEquals(HttpStatus.OK.value(), response.getStatus());
				assertEquals(response.getContentAsString(),responsePayload);

	   }
		
		
		@Test
		public void testForString() throws Exception {
			String content = "latha";
			String responsePayload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><string>latha</string>";
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("/xmlconverter")
					.accept(MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML).content(content)
					.contentType(MediaType.APPLICATION_XML);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			assertEquals(response.getContentAsString(),responsePayload);
		}
		
		@Test
		public void testForBoolean() throws Exception {
			String content = "true";
			String responsePayload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><boolean>true</boolean>";
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("/xmlconverter")
					.accept(MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML).content(content)
					.contentType(MediaType.APPLICATION_XML);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			assertEquals(response.getContentAsString(),responsePayload);
		}
		
		@Test
		public void testForNumber() throws Exception {
			String content = "2";
			String responsePayload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><number>2</number>";
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("/xmlconverter")
					.accept(MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML).content(content)
					.contentType(MediaType.APPLICATION_XML);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			assertEquals(response.getContentAsString(),responsePayload);
		}
		
		@Test
		public void testForNull() throws Exception {
			String content = "null";
			String responsePayload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><null/>";
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("/xmlconverter")
					.accept(MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML).content(content)
					.contentType(MediaType.APPLICATION_XML);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			assertEquals(response.getContentAsString(),responsePayload);
		}
		
		@Test
		public void testForJSONObject() throws Exception {
			String content = "{}";
			String responsePayload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><object/>";
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("/xmlconverter")
					.accept(MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML).content(content)
					.contentType(MediaType.APPLICATION_XML);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			assertEquals(response.getContentAsString(),responsePayload);
		}
		
		
		
		
		
}