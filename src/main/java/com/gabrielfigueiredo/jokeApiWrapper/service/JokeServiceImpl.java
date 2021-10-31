package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.enums.JokeType;

@Service
public class JokeServiceImpl implements JokeService {

	private final WebClient jokeApiClient;
	
	private static final String ANY_CATEGORY = "Any";
	private static final String LANGUAGE = "en";
	
	public JokeServiceImpl(WebClient webClient) {
		this.jokeApiClient = webClient;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean testConnection() { // Method used to test the connection with the JokeAPI
		try {
			Map<String, Object> responseBody = this.jokeApiClient
											.get()
											.uri("/ping")
											.retrieve()
											.bodyToMono(Map.class)
											.block();
			
			return (Boolean) responseBody.get("error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Joke getJoke(JokeType type, String... categories) { // Method used to get jokes
		try {
			Joke joke = this.jokeApiClient
					.get()
					.uri(uriBuilder -> uriBuilder
						.path("/joke/{category}")
						.queryParam("safe-mode") // Apply the safe mode filter
						.queryParam("amount", 1) // Grants that only one joke will be returned
						.queryParam("lang", LANGUAGE) // Force the language to be English
						.queryParamIfPresent("type", Optional.ofNullable(type)) // Add the type filter if present
						.build(categories != null ? String.join(",", categories) : ANY_CATEGORY)) // Add the category filter if present, otherwise search jokes from Any category
					.retrieve()
					.bodyToMono(Joke.class)
					.block();

			if(joke.hasError()) {
				throw new JokeNotFoundException(joke.getMessage(), joke.getAdditionalInfo());
			}
			
			return joke;
		} catch (JokeNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while fetching a new joke");
		}
	}
}
