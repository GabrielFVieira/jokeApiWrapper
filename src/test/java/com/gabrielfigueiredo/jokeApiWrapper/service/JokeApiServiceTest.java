package com.gabrielfigueiredo.jokeApiWrapper.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class JokeApiServiceTest {
	private static MockWebServer server;
	private JokeApiService service;
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@BeforeAll
	static void setUp() throws IOException {
		server = new MockWebServer();
		server.start();
	}
	
	@AfterAll
	static void teadDown() throws IOException {
		server.shutdown();
	}
	
	@BeforeEach
	void initialize() {
		String baseUrl = String.format("http://localhost:%s", server.getPort());
		service = new JokeApiService(WebClient.create(baseUrl));
	}
	
	@Test
	void testConnection() throws Exception {
		Map<String, Object> response = Collections.singletonMap("error", true);
		
		server.enqueue(new MockResponse()
							.setBody(mapper.writeValueAsString(response))
							.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
		
		assertFalse(service.testConnection());
	}
	
	@Test
	void testGetApiJoke() throws Exception {
		JokeResponse expectedResponse = getTestJokeResponse(false);
		
		server.enqueue(new MockResponse()
							.setBody(mapper.writeValueAsString(expectedResponse))
							.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

		JokeResponse apiResponse = service.getApiJoke(Optional.of("single"), "en", "Any");
		assertEquals(expectedResponse, apiResponse);
		
		expectedResponse.setId(5);
		assertNotEquals(expectedResponse, apiResponse);
	}
	
	@Test
	void testErrorGettingApiJoke() throws Exception {
		Assertions.assertThrows(JokeNotFoundException.class, () -> {
			JokeResponse expectedResponse = getTestJokeResponse(true);
			
			server.enqueue(new MockResponse()
								.setBody(mapper.writeValueAsString(expectedResponse))
								.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
			
			service.getApiJoke(Optional.of("single"), "en", "Any");
		});
	}
	
	private static JokeResponse getTestJokeResponse(boolean error) {
		JokeResponse response = new JokeResponse();
		response.setId(1);
		response.setCategory("Any");
		response.setType("single");
		response.setJoke("Think in something good");
		response.setSafe(true);
		response.setLang("en");
		response.setError(error);
		response.setMessage(error ? "Error message" : null);
		response.setAdditionalInfo(error ? "Additional info" : null);
		
		return response;
	}
}
