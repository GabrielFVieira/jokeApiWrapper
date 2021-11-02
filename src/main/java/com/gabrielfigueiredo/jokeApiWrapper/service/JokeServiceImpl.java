package com.gabrielfigueiredo.jokeApiWrapper.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gabrielfigueiredo.jokeApiWrapper.exception.JokeNotFoundException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.OutOfJokesException;
import com.gabrielfigueiredo.jokeApiWrapper.exception.ServerException;
import com.gabrielfigueiredo.jokeApiWrapper.model.Joke;
import com.gabrielfigueiredo.jokeApiWrapper.repository.JokeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {
	private final JokeRepository repository;
	private final JokeApiService apiService;
	
	public Joke getJoke(Optional<String> type, String language, String... categories) { 
		try {
			JokeResponse response = apiService.getApiJoke(type, language, categories);
			
			Joke joke = new Joke();
			joke.setJokeApiId(response.getId());
			joke.setLang(response.getLang());
			joke.setCategory(response.getCategory());
			joke.setJoke(response.getCompleteJoke());
			
			if(repository.exists(Example.of(joke))) {
				throw new OutOfJokesException(); // Throw an error if the joke was already seen
			}
			
			return repository.save(joke);
		} catch (JokeNotFoundException | OutOfJokesException | ServerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while fetching a new joke");
		}
	}
	
	@Override
	public Joke find(Integer jokeApiId, String language) {
		try {
			Joke joke = new Joke();
			joke.setJokeApiId(jokeApiId);
			joke.setLang(language);
			
			Optional<Joke> savedJoke = repository.findOne(Example.of(joke));
			if(!savedJoke.isPresent()) {
				throw new JokeNotFoundException("This joke hasn't been seen yet");
			}
			
			return savedJoke.get();
		} catch (JokeNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Error while searching for the joke with id: " + jokeApiId + " and language: " + language);
		}
	}
	

	@Override
	public List<Joke> getTopJokes(String category, String language) {
		return getTopJokes(category, language, Integer.valueOf(JokeApiService.DEFAULT_TOP_AMOUNT));
	}
	
	@Override
	public List<Joke> getTopJokes(String category, String language, Integer amount) {
		try {
			if(Strings.isEmpty(category) || JokeApiService.DEFAULT_CATEGORY.equals(category.toLowerCase())) {
				return repository.listTopJokes(language, Pageable.ofSize(amount));
			}
			
			return repository.listTopJokes(category, language, Pageable.ofSize(amount));
		} catch (Exception e) {
			throw new ServerException("Error while searching for the top " + amount + 
									  " jokes in " + language + " of the category " + category);
		}
	}

	@Override
	public Page<Joke> getUnratedJokes(String language, Pageable pageable) {
		try {
			return repository.listUnratedJokes(language, pageable);
		} catch (Exception e) {
			throw new ServerException("Error while searching for the unrated jokes of the language: " + language);
		}
	}
}