package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JokeApiService {
	private final WebClient jokeApiClient;
	
	public static final String DEFAULT_CATEGORY = "any";
	public static final String DEFAULT_LANGUAGE = "en";
	public static final String DEFAULT_TOP_AMOUNT = "5";
	
	// Method used to test the connection with the JokeAPI
	@SuppressWarnings("unchecked")
	public Boolean testConnection() { 
		try {
			Map<String, Object> responseBody = this.jokeApiClient
											.get()
											.uri("/ping")
											.retrieve()
											.bodyToMono(Map.class)
											.block();
			
			return !((Boolean) responseBody.get("error"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Fetch a new Joke in the JokeAPI
	public JokeResponse getApiJoke(Optional<String> type, String language, String... categories) throws JokeNotFoundException, ServerException { 
		try {
			JokeResponse response = this.jokeApiClient
					.get()
					.uri(uriBuilder -> uriBuilder
						.path("/joke/{category}")
						.queryParam("safe-mode") // Apply the safe mode filter
						.queryParam("amount", 1) // Grants that only one joke will be returned
						.queryParam("lang", language) // Set the language - DEFAULT: English
						.queryParamIfPresent("type", type) // Add the type filter if present
						.build(String.join(",", categories))) // Add the category filter - DEFAULT: Any
					.retrieve()
					.bodyToMono(JokeResponse.class)
					.block();

			if(response.getError()) {
				throw new JokeNotFoundException(response.getMessage(), response.getAdditionalInfo());
			}
			
			return response;
		} catch (JokeNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while fetching a new joke");
		}
	}
	
}
