package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.OutOfJokesException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.model.JokeRating;
import com.gabrielfigueiredo.jokeApiWrapper.util.JokeApiUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {
	private final WebClient jokeApiClient;
	private final JokeRatingService ratingService;
	private final JokeHistoryService historyService;
	
	private static final String JOKE_URI_PATH = "/joke/{category}";
	
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
	public Joke getJoke(Optional<String> type, String language, String... categories) { // Method used to get jokes
		try {
			Joke joke = this.jokeApiClient
					.get()
					.uri(uriBuilder -> uriBuilder
						.path(JOKE_URI_PATH)
						.queryParam("safe-mode") // Apply the safe mode filter
						.queryParam("amount", 1) // Grants that only one joke will be returned
						.queryParam("lang", language) // Set the language - DEFAULT: English
						.queryParamIfPresent("type", type) // Add the type filter if present
						.build(String.join(",", categories))) // Add the category filter - DEFAULT: Any
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
	public Joke find(Integer id, String language) {
		try {
			Joke joke = this.jokeApiClient
					.get()
					.uri(uriBuilder -> uriBuilder
						.path(JOKE_URI_PATH)
						.queryParam("idRange", id) // Filter by the specific id
						.queryParam("lang", language) // Set the language - DEFAULT: English
						.build(JokeApiUtils.DEFAULT_CATEGORY))
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
	public List<Joke> getTopJokes(String category, String language) {
		return getTopJokes(category, language, Integer.valueOf(JokeApiUtils.DEFAULT_TOP_AMOUNT));
	}
	
	@Override
	public List<Joke> getTopJokes(String category, String language, Integer amount) throws ServerException {
		try {
			List<JokeRating> ratings = ratingService
					.listByRatingPerCategory(category, language, amount);
			
			List<Joke> jokes = ratings.parallelStream()
									  .map(rating -> find(rating.getJokeId(), language))
									  .collect(Collectors.toList());
			
			return jokes;
		} catch (Exception e) {
			throw new ServerException("Error while searching for the top " + amount + 
									  " jokes in " + language + " of the category " + category);
		}
	}
}
