package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.OutOfJokesException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;
import com.gabrielfigueiredo.jokeApiWrapper.model.enums.JokeType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {
	private final WebClient jokeApiClient;
	private final JokeRatingService ratingService;
	private final JokeHistoryService historyService;
	
	private static final String ANY_CATEGORY = "Any";
	private static final String LANGUAGE = "en";
	private static final String JOKE_URI_PATH = "/joke/{category}";
	private static final Integer DEFAULT_TOP_RATE_AMOUNT = 5;
	
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
						.path(JOKE_URI_PATH)
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
			
			if(!historyService.isNewJoke(joke)) {
				throw new OutOfJokesException();
			}
			
			return joke;
		} catch (JokeNotFoundException | OutOfJokesException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while fetching a new joke");
		}
	}
	
	@Override
	public Joke find(Integer id) {
		try {
			Joke joke = this.jokeApiClient
					.get()
					.uri(uriBuilder -> uriBuilder
						.path(JOKE_URI_PATH)
						.queryParam("idRange", id) // Filter by the specific id
						.queryParam("lang", LANGUAGE) // Force the language to be English
						.build(ANY_CATEGORY))
					.retrieve()
					.bodyToMono(Joke.class)
					.block();
			
			if(joke.hasError()) {
				throw new JokeNotFoundException(joke.getMessage());
			}
			
			return joke;
		} catch (JokeNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while searching for the joke with id: " + id);
		}
	}
	

	@Override
	public List<Joke> getTopJokes(String category, Integer amount) {
		amount = amount != null ? amount : DEFAULT_TOP_RATE_AMOUNT;
		try {
			List<JokeRating> ratings = ratingService
					.listByRating(category, amount);
			List<Joke> jokes = new ArrayList<>();
			ratings.forEach(rating -> jokes.add(find(rating.getJokeId())));
			
			return jokes;
		} catch (Exception e) {
			throw new ServerException("Error while searching for the top " + amount + " jokes of the category " + category);
		}

	}
}
